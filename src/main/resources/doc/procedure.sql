CREATE PROCEDURE calcSafeStock(IN storeNo VARCHAR(2))
BEGIN
	DECLARE end_flag INT DEFAULT 0;
	DECLARE matNo BIGINT;
	DECLARE matCode VARCHAR(10);
	DECLARE sumTime INT;
	DECLARE cnt INT;
	DECLARE period INT;
	DECLARE nowDate DATETIME DEFAULT NOW();
	DECLARE amount DECIMAL(13, 2);
	DECLARE minNeed DECIMAL(13, 2);

	DECLARE mat_cursor CURSOR FOR
		SELECT s.mat_no,m.mat_code
		FROM m_stock s
		INNER JOIN m_material m ON s.mat_no=m.mat_no
		WHERE s.store_no=storeNo;

	DECLARE CONTINUE HANDLER FOR NOT FOUND SET end_flag = 1;

	OPEN mat_cursor;

		readLoop:LOOP

			FETCH mat_cursor INTO matNo, matCode;

			IF end_flag=1 THEN
				LEAVE readLoop;
			END IF;

			IF LEFT(matCode, 1)='X' THEN
				SET matCode = SUBSTR(matCode FROM 2);
			END IF;

			SELECT IFNULL(SUM(CASE WHEN mat_amount<0 THEN -TIMESTAMPDIFF(DAY, a.purchase_date, a.occ_date) ELSE TIMESTAMPDIFF(DAY, a.purchase_date, a.occ_date) END), 0), COUNT(*)
      FROM m_erp_mat a
      WHERE IFNULL(a.purchase_date,'')!='' AND a.mat_code=matNo
			INTO sumTime, cnt;

			SELECT CASE WHEN cnt>0 THEN sumTime/cnt ELSE 0 END INTO period;

			SELECT IFNULL(SUM(out_amount), 0)
			FROM m_out
			WHERE draw_step='7202F' AND mat_no=matNo AND store_no=storeNo AND out_date>=DATE_FORMAT(DATE_ADD(nowDate, INTERVAL -period DAY), '%Y%m%d') AND out_date<=DATE_FORMAT(nowDate,'%Y%m%d')
			INTO amount;

			SELECT CASE WHEN period<=0 THEN 0 ELSE amount/period END INTO minNeed;

			UPDATE m_safe_stock SET red_warn=minNeed WHERE store_no=storeNo AND mat_no=matNo;
			IF ROW_COUNT()<1 THEN
				INSERT INTO m_safe_stock (store_no, mat_no, max_amount, yellow_warn, red_warn) VALUES(storeNo, matNo, minNeed*2, minNeed*0.2, minNeed);
			END IF;

		END LOOP readLoop;

	CLOSE mat_cursor;
END;
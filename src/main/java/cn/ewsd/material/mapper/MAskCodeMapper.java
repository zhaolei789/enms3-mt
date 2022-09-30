package cn.ewsd.material.mapper;
import cn.ewsd.material.model.MAskCode;
import cn.ewsd.repository.model.MIn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-23 11:07:12
 */
public interface MAskCodeMapper extends tk.mybatis.mapper.common.Mapper<MAskCode> {

    int executeSave(MAskCode mAskCode);

    List<MAskCode> getMAskCodeList(@Param("occDate1Qry") String occDate1Qry, @Param("occDate2Qry") String occDate2Qry, @Param("userTeam") String userTeam,
                                   @Param("askNameQry") String askNameQry);

    MAskCode getAskCode(String askName);

    MAskCode getAskCodeByNo(String askNo);

    int deleteByNo(String askNo);

    int updateAskCode(MAskCode mAskCode);

    List<MAskCode> getCheckIndexList(@Param("userId") String userId, @Param("userTeam") String userTeam);

    List<MAskCode> getCheckList(@Param("askTeam") String askTeam, @Param("askMonth") String askMonth, @Param("askStep") String askStep);

    List<MAskCode> getMakeBackList(@Param("date1Qry") String date1Qry, @Param("addrQry") String addrQry, @Param("matQry") String matQry, @Param("teamQry") String teamQry, @Param("flagQry") boolean flagQry,
                                   @Param("curDate") String curDate, @Param("stepQry") String stepQry, @Param("day1Qry") String day1Qry, @Param("day2Qry") String day2Qry, @Param("needTypeQry") String needTypeQry);

    List<MIn> getMakeBackDetailList(@Param("startDateQry") String startDateQry, @Param("endDateQry") String endDateQry, @Param("matNameQry") String matNameQry);

    MAskCode getMakeBack(String askNo);

    List<MAskCode> getFileListByAskNo(String askNo);

    int getAskCodeFileNum(String askNo);

    List<MAskCode> getCheckPendingRecords(@Param("userId") String userId, @Param("userTeam") String userTeam);
}

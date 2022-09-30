package cn.ewsd.base.utils;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Random;

public class Snow {

    /** ʱ�䲿����ռ���� */
    private static final int TIME_LEN = 41;
    /** ��������id��ռ���� */
    private static final int DATA_LEN = 5;
    /** ����id��ռ���� */
    private static final int WORK_LEN = 5;
    /** �����ڴ�������ռ���� */
    private static final int SEQ_LEN = 12;

    /** ������ʼʱ�� 2020-07-27*/
    private static final long START_TIME = 1595835560497L;
    /** �ϴ�����ID��ʱ��� */
    private static long LAST_TIME_STAMP = -1L;
    /** ʱ�䲿�������ƶ���λ�� 22 */
    private static final int TIME_LEFT_BIT = 64 - 1 - TIME_LEN;

    /** �Զ���ȡ��������id�������ֶ�����0-31֮������� */
    private static final long DATA_ID = getDataId();
    /** �Զ�����id�������ֶ�����0-31֮������� */
    private static final long WORK_ID = getWorkId();
    /** ��������id���ֵ 31 */
    private static final int DATA_MAX_NUM = ~(-1 << DATA_LEN);
    /** ����id���ֵ 31 */
    private static final int WORK_MAX_NUM = ~(-1 << WORK_LEN);
    /** �����ȡ��������id�Ĳ��� 32 */
    private static final int DATA_RANDOM = DATA_MAX_NUM + 1;
    /** �����ȡ����id�Ĳ��� 32 */
    private static final int WORK_RANDOM = WORK_MAX_NUM + 1;
    /** ��������id����λ�� 17 */
    private static final int DATA_LEFT_BIT = TIME_LEFT_BIT - DATA_LEN;
    /** ����id����λ�� 12 */
    private static final int WORK_LEFT_BIT = DATA_LEFT_BIT - WORK_LEN;

    /** ��һ�κ����ڴ�����ֵ */
    private static long LAST_SEQ = 0L;
    /** �����ڴ��е����ֵ 4095 */
    private static final long SEQ_MAX_NUM = ~(-1 << SEQ_LEN);

    /**
     * ��ȡ�ַ���S���ֽ����飬Ȼ�������Ԫ����ӣ��ԣ�max+1��ȡ��
     * @param s ���ػ�����hostName/hostAddress
     * @param max ����/������id���ֵ
     * @return
     */
    private static int getHostId(String s, int max) {
        byte[] bytes = s.getBytes();
        int sums = 0;
        for (int b : bytes) {
            sums += b;
        }
        return sums % (max + 1);
    }

    /**
     * ���� host address ȡ�࣬ �����쳣�ͷ��� 0-31 ֮��������
     * @return ����ID
     */
    private static int getWorkId() {
        try {
            return getHostId(Inet4Address.getLocalHost().getHostAddress(), WORK_MAX_NUM);
        } catch (UnknownHostException e) {
            return new Random().nextInt(WORK_RANDOM);
        }
    }

    /**
     * ���� host name ȡ�࣬ �����쳣�ͷ��� 0-31 ֮��������
     * @return ����ID����������ID��
     */
    private static int getDataId() {
        try{
            return getHostId(Inet4Address.getLocalHost().getHostName(), DATA_MAX_NUM);
        }catch(Exception e){
            return new Random().nextInt(DATA_RANDOM);
        }
    }

    /**
     * ��ȡ��һ��ͬ�����ʱ���
     * @param lastMillis
     * @return ��һ�����ʱ���
     */
    private static long nextMillis(long lastMillis) {
        long now = System.currentTimeMillis();
        while (now <= lastMillis) {
            now = System.currentTimeMillis();
        }
        return now;
    }

    /**
     * �����㷨����Ҫ������֤������ȫ
     * @return ����ΨһID
     */
    public synchronized static long getUUID() {
        long now = System.currentTimeMillis();

        // �����ǰʱ��С����һ��ID���ɵ�ʱ�����˵��ϵͳʱ�ӻ��˹�����ʱ���׳��쳣
        if (now < LAST_TIME_STAMP) {
            throw new RuntimeException(String.format("ϵͳʱ����� %d �����ھܾ�����ѩ��ID", START_TIME));
        }

        if (now == LAST_TIME_STAMP) {
            LAST_SEQ = (LAST_SEQ + 1) & SEQ_MAX_NUM;
            if (LAST_SEQ == 0) {
                now = nextMillis(LAST_TIME_STAMP);
            }
        } else {
            LAST_SEQ = 0;
        }

        // �ϴ�����ID��ʱ���
        LAST_TIME_STAMP = now;

        return ((now - START_TIME) << TIME_LEFT_BIT | (DATA_ID << DATA_LEFT_BIT) | (WORK_ID << WORK_LEFT_BIT) | LAST_SEQ);
    }

    /**
     * ����������
     * @param args
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int num = 20;
        for (int i = 0; i < num; i++) {
            System.out.println(getUUID());
        }
        long end = System.currentTimeMillis();

        System.out.println("������ " + num + " ��ID����ʱ " + (end - start) + " ����");
    }
    //30131 12035 76341 03
    //13177 85739 13135 5139
    //92233 72036 85477 5807 SQLSERVER BIGINT
    //92233 72036 85477 5807 JAVA long�����ֵ
}

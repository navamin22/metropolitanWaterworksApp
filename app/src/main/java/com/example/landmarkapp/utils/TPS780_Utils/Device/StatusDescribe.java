package com.example.landmarkapp.utils.TPS780_Utils.Device;


import java.util.Arrays;

public class StatusDescribe {

    public static String getStatusDescribe(int getStatus) {
        Arrays.fill(statusList,0);
        try {
            int status = getStatus;
            if(status==-1){
                statusList[0]=1;
                return "Data transmission error. Please check the connection and resend it.";//数据传输错误,请检查连接并重新发送
            }
            StringBuilder builder = new StringBuilder();
            StringBuffer descriptBuffer = new StringBuffer();
            StringBuffer troubleBuffer = new StringBuffer();
            //传感应状态
            if ((status & 0x200) > 0) {
                statusList[1]=1;
                descriptBuffer.append("Nopaper, ");//[1]少纸
                troubleBuffer.append("PaperFew|");

            }


            if ((status & 0x400) > 0||(status & 0x08) > 0 ) {
                statusList[2]=1;
                descriptBuffer.append("Nopaper, ");//缺纸
                troubleBuffer.append("OutOfPaper|");

            }
            //脱机状态
            if ((status & 0x4) > 0) {
                descriptBuffer.append("Error, ");//发生错误
                troubleBuffer.append("happen error|");

            }
            if ((status & 0x20) > 0) {
                statusList[4]=1;
                descriptBuffer.append("Cover open, ");//盖板打开
                troubleBuffer.append("box open|");

            }
            //打印机状态
            if ((status & 0x1) > 0) {
                statusList[3]=1;
                descriptBuffer.append("Offline, ");//脱机
                troubleBuffer.append("Offline|");

            }
            if ((status & 0x2) > 0 || (status & 0x10) > 0) {
                descriptBuffer.append("feeding, ");//[8]
                troubleBuffer.append("feeding|");

            }

            //错误状态
            if ((status & 0x100) > 0) {
                statusList[5]=1;
                descriptBuffer.append("MachineError, ");//机械错误
                troubleBuffer.append("MachineError|");

            }


            if ((status & 0x40) > 0) {
                statusList[6]=1;
                descriptBuffer.append("Automatic recovery of errors, ");//可自动恢复错误
                troubleBuffer.append("CorrectingError|");
            }


            if ((status & 0x80) > 0) {
                statusList[7]=1;
                descriptBuffer.append("Nonrecoverable error, ");//不可恢复错误
                troubleBuffer.append("NotCorrectError|");

            }

            String descript = descriptBuffer.toString().trim();

            if (!descript.isEmpty()) {
                descript = descript.substring(0, descript.length() - 1);
            } else {
                descript = "Normal";//正常
            }



            return descript;
        } catch (Exception e) {

            return "Offline123123";
        }
    }

    public  static int[] statusList = {        //错误列表
            //数据传输错误0
            0,
            0,                            //少纸1
            0,                            //缺纸2
            0,                            //脱机3
            0,                            //机头抬起4
            0,                            //机械错误5
            0,                            //可自动恢复错误6
            0,                            //不可恢复错误7
            //offline130

    };
    public static boolean isoverheating(){
        if (statusList[6] == 1) {
            return true;
        }
        return false;
    }

    /**
     * 判断打印机是否可以正常打印
     */
    public static boolean isPrinterOk() {
        for (int i = 0; i < statusList.length; i++) {
            if (statusList[i] != 0) {
                return  false;
            }
        }
        return true;
    }

    /**
     * 判断是否少纸
     * @return
     */
    public static boolean isPaperDone(){
        if (statusList[1] == 1) {
            return true;
        }
        return false;
    }

    /**
     * 判断缺纸
     *
     * @return
     */
    public static boolean isOutOfPaper() {
        if (statusList[2] == 1) {
            return true;
        }
        return false;
    }
    public static boolean isOffline() {
        if (statusList[0] == 1||statusList[3]==1) {
            return true;
        }
        return false;
    }
    public static boolean Headlift(){
        if (statusList[4] == 1) {
            return true;
        }
        return false;
    }
    /**
     * 判断脱机
     *
     * @return
     */

}
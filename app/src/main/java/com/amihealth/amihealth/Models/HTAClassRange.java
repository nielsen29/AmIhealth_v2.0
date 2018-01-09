package com.amihealth.amihealth.Models;

import com.amihealth.amihealth.Configuraciones.SessionManager;

/**
 * Created by amihealthmel on 11/14/17.
 */

public class HTAClassRange {

    public int getRango(int SYS, int DIS, int pulso, String classRange){

        switch (classRange){

            case "1":

                return getSECRange(SYS, DIS, pulso);

            default:

                return getSECRange(SYS, DIS, pulso);

        }
    }

    private int getSECRange(int SYS, int DIS, int pulso) {

        if(SYS >= 140 && DIS < 90){

            return 7 + 1;

        }
        else{

            int datasys     = evaluarSys(SYS);
            int datadis     = evaluarDis(DIS);

            if(datasys == datadis){

                return datasys + 1;

            }
            else {
                if(datasys > datadis){

                    return datasys + 1;

                }
                else{

                    return  datadis + 1;

                }
            }
        }
    }



    private int evaluarSys(int sys){

        if(sys < 120) {

            return 1;

        } else {

            if (sys >= 120 && sys <= 129) {

                return 2;

            } else {
                if (sys >= 130 && sys <= 139) {

                    return 3;

                } else {

                    if (sys >= 140 && sys <= 159) {

                       return 4;

                    } else {

                        if (sys >= 160 && sys <= 179) {

                           return 5;

                        } else {

                            return 6;
                        }

                    }

                }

            }

        }

    }

    private int evaluarDis(int dis){

        if (dis < 80) {

           return 1;
        }
        else{

            if(dis >= 80 && dis <= 84){

                return 2;
            }

            else{

                if(dis >= 85 && dis <= 89){

                    return 3;

                }

                else{

                    if (dis >= 90 && dis <= 99){

                        return 4;

                    }

                    else {

                        if(dis >= 100 && dis <= 109){

                            return  5;

                        }

                        else{

                             return 6;

                        }
                    }
                }
            }
        }
    }


}

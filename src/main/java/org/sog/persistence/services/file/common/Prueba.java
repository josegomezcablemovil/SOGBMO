package org.sog.persistence.services.file.common;

import com.jcraft.jsch.ChannelSftp.LsEntry;
import java.util.Vector;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author jeisson.junco
 */
@ManagedBean(name = "prueba")
@ViewScoped
public class Prueba {

    public Prueba() {

        SFTPBean sftpBean = new SFTPBean();

        boolean blResult = sftpBean.connect("10.30.3.5", 22, "root", "123456@123");

        if (blResult) {
            System.out.println("Connect successed");

            //now we will try to download file
//			blResult = sftpBean.uploadFile( "E:\\test.c","/test/16LF1824_6.c_1");
//			if(blResult) {
//				System.out.println("upload successed");
//			}
//			else {
//				System.out.println("u failed");
//			}
////			//in here i demo list file first.
            //checking again file that u have just uploaded file
            Vector<LsEntry> vtFiles = sftpBean.listFile("/test");

            if (vtFiles != null) {
                for (LsEntry lsEntry : vtFiles) {
                    System.out.println(lsEntry.getFilename() + "\r\n");
                }
            }
            sftpBean.close();
        } else {
            System.out.println("Connect failed.");

        }
    }
}

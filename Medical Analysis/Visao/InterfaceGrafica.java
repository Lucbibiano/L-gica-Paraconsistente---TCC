package Visao;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class InterfaceGrafica {

	public void aplicaInterfaceNimbus(){
		try{
			for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
				if("Nimbus".equals(info.getName())){
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}catch(Exception e){
			System.out.println("N�o foi poss�vel criar a interface gr�fica a partir do Nimbus.");
		}
	
		}
}
	

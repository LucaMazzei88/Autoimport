package importCat;

import strategy.ConcreteImport;
import strategy.ImportStrategy;
import strategy.LoginStrategy;
import strategy.SynchStrategy;

public class Test {

	
	public static void main(String[] args) throws Exception {
		Driver dr = null;
		
		try {
			dr = Driver.createDriver("chromedriver.exe");
			ImportStrategy login=new LoginStrategy();
			login.execute();
			ImportStrategy imp;
			if(dr.getUrl().contains("qafull") || dr.getUrl().contains("cpqbitwin4")){
				System.out.println(dr.getUrl());
				imp=new ConcreteImport("Import Object New");
			}else{
				imp=new ConcreteImport("Import Object");
			}
			imp.execute();
			ImportStrategy synch=new SynchStrategy();
			synch.execute();
//			dr.vaiAllaPagina("C:\\Users\\ThinkOpen\\Desktop\\provaHtml.html");
//			dr.cercaErrori(By.xpath("//tr[@class=\"showborder\"]"),"showborder");
//			Thread.sleep(1000);
			dr.arresta();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(dr!=null)
				dr.arrestaService();
			e.printStackTrace();
		}
		

	}
	
	
}

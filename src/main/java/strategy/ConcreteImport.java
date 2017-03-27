package strategy;

import org.openqa.selenium.By;

import importCat.Driver;
import importCat.wrongPopUpException;

public class ConcreteImport extends ImportStrategy {

	private final String REQUESTID="Bit2WinImport?requestId=";
	private final String STARTFROM="&startFrom=0";
	private String objectOrNew; 
	public ConcreteImport(String objectOrNew) {
		super();
		this.objectOrNew = objectOrNew;
	}
	@Override
	public void execute() throws Exception {
		String url="";
		String url2;
		if((getStatus()==ImportStrategy.UNLOGGED)){
			return;
		}
		Driver dr;
		String temp;

		dr = Driver.createDriver("chromedriver.exe");
		dr.alertJS("Navigo nella pagina di import, attendi il prossimo alert...");
		dr.click(By.cssSelector("img.allTabsArrow"), "allTabsArrow", true);
		dr.click(By.linkText("Bit2Win"),"Bit2Win", true);
		dr.click(By.linkText("Deploy"),"Deploy", true);
		dr.aspetta(objectOrNew);
		System.out.println(dr.esiste(objectOrNew));
		dr.clickByHref(By.partialLinkText(objectOrNew),objectOrNew, true);
		dr.click(By.id("loadFilesButton"),"loadFilesButton", true);
		dr.alertJS("Trascina gli zip nella finestra di caricamento, poi il processo sarà automatizzato. Al Termine verrà visualizzato l alert di completa installazione");
		dr.aspetta("CatalogItem__");
		dr.click(By.xpath("(//button[@onclick='hideModal();return false;'])[2]"),"style=\"display: inline-block;\">Close</button>", true);
		if(dr.esiste("id=\"checkDataMapID\" onclick=\"checkDataMap();return false;\" disabled=\"\"")){
			url=dr.getUrl();
			url=url.substring(0, url.lastIndexOf("/")+1);
			url2=dr.getAttribute(By.linkText("Go To Request"), "Go To Request", "onclick");
			url2=url2.split("'")[1].substring(1);
			dr.vaiAllaPagina(url+REQUESTID+url2+STARTFROM);
		}
		dr.click(By.id("checkDataMapID"), "checkDataMapID", true);
		dr.notAspetta("id=\"startImportButton\" onclick=\"hideModalDataMap();startImport();return false;\" style=\"margin-right: 2%; display: none;");
		dr.click(By.id("startImportButton"),"startImportButton", true);
		if(!(temp=dr.closeAlertAndGetItsText()).equals("Are you sure?")){
			throw new wrongPopUpException(temp);
		}
		while(!dr.isAlertPresent()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(!(temp=dr.closeAlertAndGetItsText()).equals("To complete the import, start the synchronization")){
			throw new wrongPopUpException(temp);
		}
		dr.vaiAllaPagina(getBaseUrl());

	}

}

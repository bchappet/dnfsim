package main.java.model;

import java.lang.reflect.InvocationTargetException;

import main.java.initRecherche.ModelCNFTPredictive;
import main.java.modelCMSVA.ModelCMSVA;
import main.java.reservoirComputing.ModelESN;


public enum Models{
	CNFT("CNFT",ModelCNFT.class,false),
	CNFTFFT("CNFTFFT",ModelCNFTFFT.class,false),
	CNFTDNFSOM("DNFSomCNFT",ModelDNFSomCNFT.class,false),
	GSPIKE("GSpike",ModelGSpike.class,false),
	GSPIKEFFT("GSpikeFFT",ModelGSpikeFFT.class,false),
	GSPIKEFFT2("GSpikeFFTAsynch",ModelGSpikeFFT.class,true),
	ESPIKE("ESpike",ModelESpike.class,false),
	ESPIKEFFT("ESpikeFFT",ModelESpike.class,false),
	ESPIKE2("ESpike2",ModelESpike2.class,false),
	ESPIKEFileReader("ESpikeFileReader",ModelESpikeFileReader.class,false),
	ESPIKEDNFSUM("DNFSomESpike",ModelDNFSomESpike.class,false),
	NSPIKE("NSpike",ModelNSpike.class,false),
	NSPIKEFileReader("NSpikeFileReader", ModelNSpikeFileReader.class,false),
	NSPIKE_PRECISION("NSpikePrecision",ModelNSpikePrecision.class,false),
	NSPIKE2("NSpikeAssynch",ModelNSpike2.class,true),
	BILAYER_SPIKE("BilayerSpike",ModelBilayerSpike.class,false),
	RSDNF("RSDNF",ModelRSDNF.class,false),
	RSDNF_MIXTE("RSDNF_Mixte",ModelRSDNFMixte.class,false),
	HARDWARE("Hardware",ModelHardware.class,false),
	CNFT_SLOW("CNFTAssynch",ModelCNFTSlow.class,true),
	CMSVA("CMSVA",ModelCMSVA.class,false),
	NSPIKE_REAL_PARAM("NSPikeRealParam",ModelNSpikeWithTrueParameters.class,false),
	MODEL_Hardware_VALIDATION("HardwareValidation",ModelHardwareValidation.class,false),
	TEST_SOM("TestSOM",TestSOM.class,false),
	DNF_SOM("DNFSom",ModelDNFSom.class,false),
	DNF_SOM_Supervise("DNFSomSupervise",ModelDNFSomSupervise.class,false),
	CNFT_InputFile("CNFTInputFile",ModelCNFTInputFile.class,false),
	MVT_DETECTION("MvtDetection",ModelMvtDetection.class,false),
	CNFT_PREDICTIVE("CNFTPredictive",ModelCNFTPredictive.class,false),
	ESN("ESN",ModelESN.class,false);
	
	private final boolean assynch;
	private final String name;
	private final Class<? extends Model> classe;
	
	Models(String name,Class<? extends Model> classe,boolean assynch){
		this.name = name;
		this.classe = classe;
		this.assynch = assynch;
	}
	
	public Model construct(){
		try {
			Model ret =  (Model) classe.getConstructors()[0].newInstance(this.name);
			if(assynch){
				ret.setAssynchronousComputation(true);
			}
			return ret;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Models getModel(String modelName) throws Exception{
		for(Models m : Models.values()){
			if(m.name.equals( modelName)){
				return m;
			}
		}
		throw new Exception("Model " + modelName+ " does not exist");
	}
	/**
	 * Return model name list
	 * @return
	 */
	public static String[] nameList(){
		Models[] models = Models.values();
		String[] ret = new String[models.length];
		for(int i = 0 ; i < ret.length ; i++){
			ret[i] = models[i].getName();
		}
		return ret;
	}

	public String getName() {
		return name;
	}
	
	
}

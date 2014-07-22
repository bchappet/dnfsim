package main.java.network.generic;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import main.java.maps.Map;
import main.java.maps.Var;
import main.java.network.generic.packet.Packet;
import main.java.network.generic.packet.Spike;
import main.java.space.Space2D;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;


/**
 * Lit un fichier .stimulis
 * @author CARRARA Nicolas
 *
 */
public class StimulisMap<P extends Packet> extends Map {//implements /*HasChildren<Ajout<P>>,*/Parameter<Ajout<P>>/*,Computable*/{

	private Element racine;
	private Document document;
	private List<Ajout<P>> ajouts;
	private BigDecimal nextTimeValue;
	private Element nextTime;
	private final int FILE = 0;
	private Iterator<Element> times;
	private Class packetclass;
	private Constructor packetConstructor;

	public StimulisMap(Var<String> file,Var<BigDecimal> dt,Var<Integer> size) throws DataConversionException, NetworkException{
		super("Stimulis Map",dt,new Space2D(size, size),file);
		try
		{
			getParameters().add(file);
			SAXBuilder sxb = new SAXBuilder();
			//On crée un nouveau document JDOM avec en argument le fichier XML
			//Le parsing est terminé ;)
			document = sxb.build(new File(file.get()));
			//On initialise un nouvel élément racine avec l'élément racine du document.
			racine = document.getRootElement();
			packetclass = Class.forName(racine.getAttribute("class").getValue());
			packetConstructor = packetclass.getConstructors()[0];//todo pas beau, faire avec getDeclared pour recup celui avec Object ... en params
			times = racine.getChildren("time").iterator();
			if(keepCompute = times.hasNext()){
				nextTime = (Element) times.next();
				nextTimeValue = new BigDecimal(nextTime.getAttribute("t").getFloatValue());
			}
			ajouts = new ArrayList<>();
		}catch (ClassNotFoundException e) {
			throw new NetworkException("La classe des packets spécifiée dans "+file.get()+" est incorrect");
		}catch(Exception e){
			throw new NetworkException("Erreur lecture xml : "+e);
		}

	}

	private boolean keepCompute = true;



	@Override
	public void compute() {
		if(keepCompute){
			try {
				// todo faire plus jolie ici
				if(nextTimeValue.subtract((getTime().subtract((BigDecimal)getDt().get()))).doubleValue() < 0.0000001){
					List<Element> adds = nextTime.getChildren();
					for(Element add: adds){
						List<Element> paramsElement = add.getChildren();
						String[] params = new String[paramsElement.size()];
						for(int i = 0;i<params.length;i++){
							params[i] = paramsElement.get(i).getText();
						}
						ajouts.add(new Ajout(add.getAttribute("indice").getIntValue(),(P)packetConstructor.newInstance(new Object[]{params})));
					}
					if(keepCompute = times.hasNext()){
						nextTime = (Element) times.next();
						nextTimeValue = new BigDecimal(nextTime.getAttribute("t").getFloatValue());
					}
				}else{
					ajouts.clear();
				}
			} catch (DataConversionException
					| InstantiationException
					| IllegalAccessException
					| IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Ajout<P> getIndex(int index) {
		return ajouts.get(index);
	}

	@Override
	public List<Ajout<P>> getValues() {
		return ajouts;
	}

	@Override
	public void reset() {
		super.reset();
		try {
			//On initialise un nouvel élément racine avec l'élément racine du document.
			racine = document.getRootElement();
			times = racine.getChildren("time").iterator();
			if(keepCompute = times.hasNext()){
				nextTime = (Element) times.next();
				nextTimeValue = new BigDecimal(nextTime.getAttribute("t").getFloatValue());
				ajouts.clear();
			}
		} catch (DataConversionException e) {
			System.err.println("Erreur lecture xml : "+e);
		}
	}
}

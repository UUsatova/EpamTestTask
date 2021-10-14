import java.util.*;
import java.io.File;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
class Gadget {

    private String brand;
    private String model;
    private int price;

    public Gadget( String brand,String model,int price){
        this.brand = brand;
        this.model = model;
        this.price = price;

    }

    public String getBrand(){
        return brand;
    }

    public String getModel(){
        return model;
    }

    public int getPrice(){
        return price;
    } 

}

public class Shop {
    
    private Map<String, List<Gadget>> typesOfProcucts;  //ключи: все разновидности продукции в магазине 

    public Shop(){
        
        typesOfProcucts = new HashMap<>();
    }

    public static List<String> setStringList(NodeList nodeList){
        List<String> Ar = new ArrayList<>();
        for(int i = 0; i < nodeList.getLength(); i++){

            if(nodeList.item(i) instanceof Element) {

                Ar.add( ((Element) nodeList.item(i)).getTagName());
            } 
          
        }
        return Ar;
        
    }

    public void getGadgets(NodeList employeeElements, String item){

        for (int i = 0; i < employeeElements.getLength(); i++) {

            Node employee = employeeElements.item(i);
            NamedNodeMap attributes = employee.getAttributes();
            Gadget aa = new Gadget(attributes.getNamedItem("brand").getNodeValue(), attributes.getNamedItem("model").getNodeValue(), Integer.parseInt(attributes.getNamedItem("price").getNodeValue()));
            typesOfProcucts.get(item).add(aa);

        }
    }

    public static int commonPrice(List<Gadget> s){

        int sum = 0;
        for (Gadget item : s){
            sum = sum + item.price;
        }
        return sum;
        
    }

    public void unitProductInformation(List<Gadget> s){
            for (Gadget item : s)
                System.out.println(String.format("Информации о продукте :  бренд - %s ,модель - %s, стоимость - %s   ", item.getBrand(),item.getModel(),item.getPrice()));
    
    }
    
    public void allShopInformation(){
        Set<String> help = typesOfProcucts.keySet();
        for(String item : help){
            System.out.println( item );
            unitProductInformation(typesOfProcucts.get(item));
            System.out.println( " общаяя стоимость " +  commonPrice(typesOfProcucts.get(item)));
              
         }

    }
   
    public void shopCreation(Document document){
        Element element  =   document.getDocumentElement();
        List<String> help =  setStringList(element.getElementsByTagName("typesOfProcucts").item(0).getChildNodes());

        for(int i = 0; i < help.size(); i++){

        typesOfProcucts.put(help.get(i),new ArrayList<Gadget>());

        }

        for(String item : help){

            if(typesOfProcucts.containsKey(item)) {

                NodeList employeeElements =  document.getDocumentElement().getElementsByTagName("") ;
                if(item.contains("Phone")) employeeElements =  document.getDocumentElement().getElementsByTagName("Phone");
                if(item.contains("Tablet")) employeeElements = document.getDocumentElement().getElementsByTagName("Tablet");
                if(item.contains("Smart_watch")) employeeElements = document.getDocumentElement().getElementsByTagName("Smart_watch");
                if(item.contains("Ebook")) employeeElements = document.getDocumentElement().getElementsByTagName("Ebook");
                getGadgets(employeeElements, item);

            }
            
            
        }

    }
    public static void main(String[] args) throws ParserConfigurationException, SAXException,IOException{
       
        Shop a = new Shop();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document =  builder.parse(new File("//home//uliana//programming//jaVka//Univer//src//classwork//gadgets.xml"));
        a.shopCreation(document);
        a.allShopInformation();

    }
}

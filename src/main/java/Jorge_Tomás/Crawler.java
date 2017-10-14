package Jorge_Tomás;

/**
 * Created by tomas on 09/10/2017.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;

import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


public class Crawler {
    private List <String> pages;
    private List <String> adverts;
    private List <List<String>> advertDetails;
    private Advertisements advertisements;


    public Crawler() {
        pages = new ArrayList <>();
        adverts = new ArrayList <>();
        advertDetails = new ArrayList <>();
        advertisements = new Advertisements();
    }

    // Gets all the 'destaques' web pages
    public void getWebPages(String URL) {
        pages.add(URL);
        try {
            Document document = Jsoup.connect(URL).get();
            Elements otherPages = document.select("a[href^=\"https://www.standvirtual.com/destaques/?page=\"]");

            for (Element page : otherPages) {
                String pageHref= page.attr("abs:href");
                if (!pages.contains(pageHref)) {
                    pages.add(pageHref);
                }
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println(pages);

    }

    //Connect to each 'destaques' page get all car advert links
    public void getAdvertLink() {
        pages.forEach(page -> {
            Document document;
            try {
                document = Jsoup.connect(page).get();
                Elements advertBoxes = document.getElementsByClass("rel   img-cover");
                for (Element advert : advertBoxes) {
                    String advertHref = advert.attr("abs:href");
                    if (!adverts.contains(advertHref)) {
                        adverts.add(advertHref);
                    }
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });
        System.out.println(adverts);
    }

    //Connect to each advert and scrape the details
    public void getAdvertDetails() {
        adverts.forEach(advert -> {
            Document document;
            try {
                document = Jsoup.connect(advert).get();
                Advertisements.Advert new_advert = new Advertisements.Advert();
                Elements advertDetails = document.getElementsByClass("offer-params__item");
                Elements advertExtras = document.getElementsByClass("offer-features__item");
                String advertID = document.getElementsByClass("offer-meta__value").last().text();
                String advertPrice = document.getElementsByClass("offer-price__number").text();
                Advertisements.Advert.Price price = new Advertisements.Advert.Price();
                price.setValue(Integer.parseInt(advertPrice.replaceAll("[^\\d]", "")));
                price.setUnits("€");
                new_advert.setPrice(price);
                new_advert.setId(Long.parseLong(advertID));
                new_advert.setUrl(advert);
                for (Element detail : advertDetails) {
                    Elements children = detail.children();
                    String detailKey = children.first().text();
                    String detailValue = children.last().text();

                    if (detailKey.equals("Anunciante")) {
                        new_advert.setAdvertiser(detailValue);
                    } else if (detailKey.equals("Marca")) {
                        new_advert.setBrand(detailValue);
                    } else if (detailKey.equals("Modelo")) {
                        new_advert.setModel(detailValue);
                    } else if (detailKey.equals("Série")) {
                        new_advert.setSeries(detailValue);
                    } else if (detailKey.equals("Versão")) {
                        new_advert.setVersion(detailValue);
                    } else if (detailKey.equals("Combustível")) {
                        new_advert.setFuel(detailValue);
                    } else if (detailKey.equals("Mês de Registo")) {
                        new_advert.setMonth(detailValue);
                    } else if (detailKey.equals("Ano de Registo")) {
                        new_advert.setYear(Integer.parseInt(detailValue));
                    } else if (detailKey.equals("Quilómetros")) {
                        Advertisements.Advert.Mileage mileage = new Advertisements.Advert.Mileage();
                        mileage.setValue(Integer.parseInt(detailValue.replaceAll("[^\\d]", "")));
                        mileage.setUnits("km");
                        new_advert.setMileage(mileage);
                    } else if (detailKey.equals("Potência")) {
                        Advertisements.Advert.HorsePower hp = new Advertisements.Advert.HorsePower();
                        hp.setValue(Integer.parseInt(detailValue.replaceAll("[^\\d]", "")));
                        hp.setUnits("cv");
                        new_advert.setHorsePower(hp);
                    } else if (detailKey.equals("Cilindrada")) {
                        Advertisements.Advert.Displacement displacement = new Advertisements.Advert.Displacement();
                        displacement.setValue(Integer.parseInt(detailValue.replaceAll("[^\\d]", "")));
                        displacement.setUnits("cm3");
                        new_advert.setDisplacement(displacement);
                    } else if (detailKey.equals("VIN")) {
                        new_advert.setVin(detailValue);
                    } else if (detailKey.equals("Segmento")) {
                        new_advert.setSegment(detailValue);
                    } else if (detailKey.equals("Cor")) {
                        new_advert.setColor(detailValue);
                    } else if (detailKey.equals("Metalizado")) {
                        new_advert.setMetallic(detailValue);
                    } else if (detailKey.equals("Tipo de Caixa")) {
                        new_advert.setTransmission(detailValue);
                    } else if (detailKey.equals("Número de Mudanças")) {
                        new_advert.setNumberOfGears(Integer.parseInt(detailValue));
                    } else if (detailKey.equals("Nº de portas")) {
                        new_advert.setNumberOfDoors(Integer.parseInt(detailValue));
                    } else if (detailKey.equals("Lotação")) {
                        new_advert.setNumberOfSeats(detailValue);
                    } else if (detailKey.equals("Classe do veículo")) {
                        new_advert.setVehicleClass(detailValue);
                    } else if (detailKey.equals("Tracção")) {
                        new_advert.setTransmission(detailValue);
                    } else if (detailKey.equals("Autonomia Máxima")) {
                        Advertisements.Advert.Autonomy autonomy = new Advertisements.Advert.Autonomy();
                        autonomy.setValue(Integer.parseInt(detailValue.replaceAll("[^\\d]", "")));
                        autonomy.setUnits("km");
                        new_advert.setAutonomy(autonomy);
                    } else if (detailKey.equals("Emissões CO2")) {
                        Advertisements.Advert.CO2Emissions co2 = new Advertisements.Advert.CO2Emissions();
                        co2.setValue(Integer.parseInt(detailValue.replaceAll("[^\\d]", "")));
                        co2.setUnits("g/km");
                        new_advert.setCO2Emissions(co2);
                    } else if (detailKey.equals("IUC")) {
                        detailValue = detailValue.replaceAll(",",".");
                        Advertisements.Advert.Iuc iuc = new Advertisements.Advert.Iuc();
                        iuc.setValue(Float.parseFloat(detailValue.replaceAll("[^\\d.]", "")));
                        iuc.setUnits("€");
                        new_advert.setIuc(iuc);
                    } else if (detailKey.equals("Registo(s)")) {
                        new_advert.setNumberOfRegisters(Integer.parseInt(detailValue));
                    } else if (detailKey.equals("Origem")) {
                        new_advert.setOrigin(detailValue);
                    } else if (detailKey.equals("Livro de Revisões completo")) {
                        new_advert.setReviewBook(detailValue);
                    } else if (detailKey.equals("Não fumador")) {
                        new_advert.setNonSmoker(detailValue);
                    } else if (detailKey.equals("2º Chave")) {
                        new_advert.setSecondKey(detailValue);
                    } else if (detailKey.equals("Tecto de Abrir")) {
                        new_advert.setCabrio(detailValue);
                    } else if (detailKey.equals("Medida Jantes de Liga Leve")) {
                        Advertisements.Advert.AlloyWheelsSize alloyWheelsSize = new Advertisements.Advert.AlloyWheelsSize();
                        alloyWheelsSize.setUnits("\"");
                        alloyWheelsSize.setValue(Integer.parseInt(detailValue.replaceAll("[^\\d]", "")));
                    } else if (detailKey.equals("Estofos")) {
                        new_advert.setUpholstery(detailValue);
                    } else if (detailKey.equals("Ar Condicionado")) {
                        new_advert.setAirConditioning(detailValue);
                    } else if (detailKey.equals("Possibilidade de financiamento")) {
                        new_advert.setFinancing(detailValue);
                    } else if (detailKey.equals("Garantia do Stand")) {
                        new_advert.setWarranty(detailValue);
                    } else if (detailKey.equals("Capota")) {
                        new_advert.setRoofTop(detailValue);
                    } else if (detailKey.equals("Aceita retoma")) {
                        new_advert.setTakeBack(detailValue);
                    } else if (detailKey.equals("Filtro de Particulas")) {
                        new_advert.setParticlesFilter(detailValue);
                    } else if (detailKey.equals("Garantia mecanica fabricante até")) {
                        new_advert.setMechanicalWarranty(detailValue);
                    } else if (detailKey.equals("ou até")) {
                        new_advert.setOrUntil(detailValue);
                    } else if (detailKey.equals("Consumo Combinado")) {
                        detailValue = detailValue.replaceAll(",",".");
                        Advertisements.Advert.Consumption consumption = new Advertisements.Advert.Consumption();
                        consumption.setValue(Float.parseFloat(detailValue.replaceAll("[^\\d.]", "")));
                        consumption.setUnits("l/100km");
                        new_advert.setConsumption(consumption);
                    } else if (detailKey.equals("Jantes de Liga Leve")) {
                        new_advert.setAlloyWheels(detailValue);
                    } else if (detailKey.equals("Numero de Airbags")) {
                        new_advert.setNumberOfAirbags(Integer.parseInt(detailValue));
                    } else if (detailKey.equals("Condição")) {
                        new_advert.setCondition(detailValue);
                    } else if (detailKey.equals("Inspecção válida até")) {
                        new_advert.setInspectionValidUntil(detailValue);
                    } else if (detailKey.equals("Valor Fixo")) {
                        new_advert.setFixedValue(detailValue);
                    } else if (detailKey.equals("IVA dedutível")) {
                        new_advert.setDeductibleVAT(detailValue);
                    } else if (detailKey.equals("Matrícula")) {
                        new_advert.setPlateNumber(detailValue);
                    } else if (detailKey.equals("Capota Eléctrica")) {
                        new_advert.setElectricRoofTop(detailValue);
                    }
                    else{
                        System.out.println(detailKey);
                    }
                }

                Advertisements.Advert.Extras extras = new Advertisements.Advert.Extras();
                for (Element extra : advertExtras) {
                    extras.getExtra().add(extra.text());

                }
                new_advert.setExtras(extras);

                advertisements.getAdvert().add(new_advert);

            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });
    }

    public String marshallList() {
        String xmlToString = new String();
        try {
            File file = new File("adverts.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Advertisements.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            StringWriter stringWriter = new StringWriter();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(advertisements, file);
            jaxbMarshaller.marshal(advertisements, stringWriter);
            jaxbMarshaller.marshal(advertisements, System.out);

            xmlToString = stringWriter.toString();
            return xmlToString;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlToString;
    }


    public static void main(String[] args) {
        try {
            File file = new File("adverts.xml");
            if (file.exists() && !file.isDirectory()) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line;
                StringBuilder xmlString = new StringBuilder();

                while((line=bufferedReader.readLine()) != null){
                    xmlString.append(line.trim());
                }
                sendString((xmlString.toString()));

            } else {
                Crawler crawler = new Crawler();
                crawler.getWebPages("https://www.standvirtual.com/destaques/");
                crawler.getAdvertLink();
                crawler.getAdvertDetails();
                String xmlString = crawler.marshallList();
                sendString(xmlString);
            }
        } catch (IOException io) {
            System.err.println(io);
        }

        System.out.println("Crawler Terminated");
    }

    public static void sendString(String xmlString) {
        try {
            TopicSender sender = new TopicSender();
            sender.sendToTopic(xmlString);
            System.out.println("Message sent");
            File file = new File("adverts.xml");
            if (file.exists() && !file.isDirectory()) {
                file.delete();
            }
        } catch(NamingException ne) {
            System.out.println("Error sending XML. XML saved as adverts.xml");
        }
    }
}

package Jorge_Tomás;

/**
 * Created by tomas on 09/10/2017.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.io.IOException;


public class Crawler {
    private List <String> pages;
    private List <String> adverts;
    private List <List<String>> advertDetails;


    public Crawler() {
        pages = new ArrayList <>();
        adverts = new ArrayList <>();
        advertDetails = new ArrayList <>();
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

    public void getAdvertDetails() {
        adverts.forEach(advert -> {
            Document document;
            try {
                document = Jsoup.connect(advert).get();
                Elements advertDetails = document.getElementsByClass("offer-params__item");
                Elements advertExtras = document.getElementsByClass("offer-features__item");
                String advertID = document.getElementsByClass("offer-meta__value").last().text();
                String price = document.getElementsByClass("offer-price__number").text();
                System.out.println(advertID);
                System.out.println(advert);
                for (Element detail : advertDetails) {
                    Elements children = detail.children();
                    String detailKey = children.first().text();
                    String detailValue = children.last().text();
                    //System.out.println(detailKey + ": " + detailValue);

                    if (detailKey.equals("Anunciante")) {
                        //Set anunciante
                    } else if (detailKey.equals("Marca")) {

                    } else if (detailKey.equals("Modelo")) {

                    } else if (detailKey.equals("Série")) {

                    } else if (detailKey.equals("Versão")) {

                    } else if (detailKey.equals("Combustível")) {

                    } else if (detailKey.equals("Mês de Registo")) {

                    } else if (detailKey.equals("Ano de Registo")) {

                    } else if (detailKey.equals("Quilómetros")) {

                    } else if (detailKey.equals("Potência")) {

                    } else if (detailKey.equals("Cilindrada")) {

                    } else if (detailKey.equals("VIN")) {

                    } else if (detailKey.equals("Segmento")) {

                    } else if (detailKey.equals("Cor")) {

                    } else if (detailKey.equals("Metalizado")) {

                    } else if (detailKey.equals("Tipo de Caixa")) {

                    } else if (detailKey.equals("Número de Mudanças")) {

                    } else if (detailKey.equals("Nº de portas")) {

                    } else if (detailKey.equals("Lotação")) {

                    } else if (detailKey.equals("Classe do veículo")) {

                    } else if (detailKey.equals("Tracção")) {

                    } else if (detailKey.equals("Autonomia Máxima")) {

                    } else if (detailKey.equals("Emissões CO2")) {

                    } else if (detailKey.equals("IUC")) {

                    } else if (detailKey.equals("Registo(s)")) {

                    } else if (detailKey.equals("Origem")) {

                    } else if (detailKey.equals("Livro de Revisões completo")) {

                    } else if (detailKey.equals("Não fumador")) {

                    } else if (detailKey.equals("2º Chave")) {

                    } else if (detailKey.equals("Tecto de Abrir")) {

                    } else if (detailKey.equals("Medida Jantes de Liga Leve")) {

                    } else if (detailKey.equals("Estofos")) {

                    } else if (detailKey.equals("Ar Condicionado")) {

                    } else if (detailKey.equals("Possibilidade de financiamento")) {

                    } else if (detailKey.equals("Garantia do Stand")) {

                    } else if (detailKey.equals("Capota")) {

                    } else if (detailKey.equals("Aceita retoma")) {

                    } else if (detailKey.equals("Filtro de Particulas")) {

                    } else if (detailKey.equals("Garantia mecanica fabricante até")) {

                    } else if (detailKey.equals("ou até")) {

                    } else if (detailKey.equals("Consumo Combinado")) {

                    } else if (detailKey.equals("Jantes de Liga Leve")) {

                    } else if (detailKey.equals("Numero de Airbags")) {

                    } else if (detailKey.equals("Condição")) {

                    } else if (detailKey.equals("Inspecção válida até")) {

                    } else if (detailKey.equals("Valor Fixo")) {

                    } else if (detailKey.equals("IVA dedutível")) {

                    } else if (detailKey.equals("Matrícula")) {

                    } else if (detailKey.equals("Capota Eléctrica")) {

                    }
                    else{
                        System.out.println(detailKey);
                    }
                }

                for (Element extras : advertExtras) {
                    //System.out.println(extras.text());
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });
    }
/*
    public void writeToFile(String filename) {
        FileWriter writer;
        try {
            writer = new FileWriter(filename);
            articles.forEach(a -> {
                try {
                    String temp = "- Title: " + a.get(0) + " (link: " + a.get(1) + ")\n";
                    //display to console
                    System.out.println(temp);
                    //save to file
                    writer.write(temp);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            });
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
*/
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.getWebPages("https://www.standvirtual.com/destaques/");
        crawler.getAdvertLink();
        crawler.getAdvertDetails();
        //bwc.getCars();
        //bwc.writeToFile("´ars");
    }
}

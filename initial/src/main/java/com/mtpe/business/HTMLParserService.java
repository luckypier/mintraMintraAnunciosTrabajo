package com.mtpe.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.mtpe.model.JobAnnouncement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

/**
 * Created by precuay on 4/18/15.
 */
@Service
public class HTMLParserService {


    public static final String ELEMENT_TYPE = "href";
    public static final String PATTERN = "tarea=cargar";

    public static final String SPLIT_URL_AND = "&";
    public static final String SPLIT_URL_EQUALS = "=";

    public Set<String> getMintraConcatenatesIds(String url) throws IOException {

        //ArrayList<String> linksProccesed = new ArrayList<>();
        Set<String> linksProccesed = new HashSet<String>();

        // need http protocol
        Document scannedRecordsHtml = Jsoup.connect(url).get();

        // get page title
        //String title = scannedRecordsHtml.title();

        // get all links
        Elements links = scannedRecordsHtml.select("a[href]");
        for (Element link : links) {

            //only links to view details
            if(link.attr(ELEMENT_TYPE).contains(PATTERN)){
                //System.out.println("text : " + link.text().trim());

                String concatenated = concatCodes(link.attr(ELEMENT_TYPE).trim());

                //new record verification
                //if( ! linksConcatenatedDB.contains(concatenated) ){
                    linksProccesed.add(concatenated);
                //}

            }

        }


        return linksProccesed;
    }



    private static String concatCodes(String link) {
        //"PreviewVacantesPublicas.jsp?tarea=cargar&V_NUMREG=0000133208&V_CODINST=20100030595"
        String[] parameters = link.split(SPLIT_URL_AND);

        StringBuilder sb = new StringBuilder();
        sb.append(parameters[1].split(SPLIT_URL_EQUALS)[1]);
        sb.append(SPLIT_URL_AND);
        sb.append(parameters[2].split(SPLIT_URL_EQUALS)[1]);
        return sb.toString();

    }



    public JobAnnouncement getMintraMatchingAnnouncement(String url, String newRecord, String ... userSearchWords) {

        String[] codes = newRecord.split(HTMLParserService.SPLIT_URL_AND);
        String fullLink = url.replace("RRRRRRRRRR",codes[0]).replace("IIIIIIIIII",codes[1]);

        System.out.println("NEW JOBANNOUNCEMENT : " + fullLink);

        JobAnnouncement jobAnnouncement = null;

        try{
            Document scannedRecordHtml = Jsoup.connect(fullLink).get();

            Element printTable = scannedRecordHtml .getElementById("printTable");
            String printTableString = printTable.toString();

            for (int p = 0; p < userSearchWords.length; p++){

                if (printTableString.contains(userSearchWords[p])){
                    //System.out.println("CONTIENE");

                    jobAnnouncement = parseElement(printTable);
                    jobAnnouncement.setLink(fullLink);
                    jobAnnouncement.setCodinst(codes[1]);
                    jobAnnouncement.setStatus(JobAnnouncement.NEW_STATUS);
                    break;
                }else{
                    //System.out.println("NO CONTIENE");
                    jobAnnouncement = new JobAnnouncement();
                    jobAnnouncement.setNumreg(codes[0]);
                    jobAnnouncement.setCodinst(codes[1]);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return jobAnnouncement;
    }

    private JobAnnouncement parseElement(Element printTable){

        Elements elements = printTable.getElementsByTag("tr");

        JobAnnouncement jobAnnouncement = new JobAnnouncement();

        //fichaPedido   1
        jobAnnouncement.setNumreg(getTdValue(elements.get(1)));

        //institucion   2
        jobAnnouncement.setInstitucion(getTdValue(elements.get(2)));

        //cargo         3
        jobAnnouncement.setCargo(getTdValue(elements.get(3)));

        //requisitos    4
        jobAnnouncement.setRequisitos(getTdValue(elements.get(4)));

        //descripcion   5
        jobAnnouncement.setDescripcion(getTdValue(elements.get(5)));

        //ocupacion     6
        jobAnnouncement.setOcupacion(getTdValue(elements.get(6)));

        //nroVacantes   7
        jobAnnouncement.setNroVacantes(getTdValue(elements.get(7)));

        //nroConvocatoria   8
        jobAnnouncement.setNroConvocatoria(getTdValue(elements.get(8)));


        return  jobAnnouncement;

    }

    private String getTdValue(Element tr){
        Elements elements = tr.getElementsByTag("td");

        return elements.get(1).text();

    }


}

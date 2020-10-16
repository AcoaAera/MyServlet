import db.Query;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filePath = request.getParameter("filePath");
        File f = new File(filePath);

        response.setContentType("text/plain");
        response.setHeader("Content-disposition", "attachment; filename=" + f.getName());

        Download(f, response, filePath);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PrintWriter writer = response.getWriter();
            String keyWord = new String(request.getParameter("keyWord").getBytes("ISO-8859-1"),"UTF-8");

            System.out.println("keyWord:" + keyWord);

            ArrayList<String> paths = Query.getRomansByWord(keyWord);

            Answer(paths, writer);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Answer(ArrayList<String> paths, PrintWriter writer) {
        //writer.println("<ul style=\"margin-bottom: 0; margin-bottom: 1rem; display: -webkit-box; display: -ms-flexbox; display: flex; -webkit-box-orient: vertical; -webkit-box-direction: normal; -ms-flex-direction: column; flex-direction: column; padding-left: 0; margin-bottom: 0\">");
        if (paths.size() != 0) {
            for (String path : paths) {

                String link = "<p><a href=\"testServlet?filePath=" + path + "\">" + path + "</a>";
                writer.println(link);
            }
        } else {
            try {
                writer.println(new String("По запросу ничего не найдено".getBytes("ISO-8859-1"),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //writer.println("</ul>");
    }

    private void Download(File f, HttpServletResponse response, String filePath) throws IOException {

        String file = Files.readAllLines(Paths.get("src\\main\\basnja\\" + filePath), StandardCharsets.UTF_8).toString();
        file = file.replaceAll("\\p{P}","");
        if (f.exists()){
            try (OutputStream outputStream = response.getOutputStream()){
                String text = file;
                outputStream.write(text.getBytes());
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
package br.compneusgppremium.api.controller;

import br.compneusgppremium.api.controller.model.ProducaoModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class DownloadsController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping(path = "/api/download")
    public ResponseEntity<ByteArrayResource> downloadProducao() {
        var sql = "SELECT p FROM producao p";

        List<?> results = entityManager.createQuery(sql).setMaxResults(50).getResultList();

        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("producao");
            Row headerRow = sheet.createRow(0);

            // Definindo os cabeçalhos das colunas
            String[] columnNames = {
                    "ID",
                    "Número Etiqueta",
                    "Data Produção",
                    "Data Carcaça",
                    "Medida",
                    "Marca",
                    "Modelo",
                    "Matriz",
                    "Regra Utilizada",
                    "Tamanho Raspado",
                    "Camelback Utilizado",
                    "Antiquebra 1",
                    "Antiquebra 2",
                    "Antiquebra 3",
                    "Espessuramento",
                    "Tempo"};

            for (int i = 0; i < columnNames.length; i++) {
                headerRow.createCell(i).setCellValue(columnNames[i]);
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            int rowNum = 1;
            for (Object result : results) {
                ProducaoModel producao = (ProducaoModel) result;
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(producao.getId());
                row.createCell(1).setCellValue(producao.getCarcaca().getNumero_etiqueta());
                row.createCell(2).setCellValue(dateFormat.format(producao.getDt_create()));
                row.createCell(3).setCellValue(dateFormat.format(producao.getCarcaca().getDt_create()));
                row.createCell(4).setCellValue(producao.getCarcaca().getMedida().getDescricao());
                row.createCell(5).setCellValue(producao.getCarcaca().getModelo().getMarca().getDescricao()); // Supondo que ModeloModel tenha um campo 'nome'
                row.createCell(6).setCellValue(producao.getCarcaca().getModelo().getDescricao()); // Supondo que MedidaModel tenha um campo 'descricao'
                row.createCell(7).setCellValue(producao.getRegra().getMatriz().getDescricao());
                row.createCell(8).setCellValue(producao.getMedida_pneu_raspado());
                row.createCell(9).setCellValue(producao.getRegra().getCamelback().getDescricao());
                row.createCell(10).setCellValue(producao.getRegra().getAntiquebra1().getDescricao());
                row.createCell(11).setCellValue(producao.getRegra().getAntiquebra2().getDescricao());
                row.createCell(12).setCellValue(producao.getRegra().getAntiquebra3().getDescricao());
                row.createCell(13).setCellValue(producao.getRegra().getEspessuramento().getDescricao());
                row.createCell(14).setCellValue(producao.getRegra().getTempo());
            }

            workbook.write(out);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Rel_Producao.xlsx");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new ByteArrayResource(out.toByteArray()));
        } catch (Exception e) {
            // Log the exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

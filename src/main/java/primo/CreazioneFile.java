package primo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

import bean.BeanAttori;
import librerie.LibrerieFile;
import librerie.LogError;

public class CreazioneFile {

	private static final String ACAPO = "\n";
	private static final String SEPARATORE = " | ";

	public static int count;
	public static int countPeck;
	public static int recordEstratti;

	public static void creazioneFile(Boolean test) {
		List<BeanAttori> output;

		try {
			output = selectData(test);

			// ESPORTAZIONE DATI SU FILE TXT
			try {
				String nomeFile = "filename.txt";

				File file = new File(nomeFile);

				if (file.exists()) {
					file.delete();
					System.out.println("File già presente. Cancellato");
				}
				if (file.createNewFile()) {
					System.out.println("File created: " + file.getName());
					writeFile(output, nomeFile, file);

//						Map<String, Object> params = new HashMap<StringObject>();
//						params.put("var1", recordEstratti); 
//						params.put("var2",LibrerieVarie.getData(Constants.timestampPattern1)); 
//						params.put("var3", count); 

					insertReport();
				} else {
					System.out.println("File already exists.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// METODO PER AGGIUNGERE IN UNA TABELLA L'ESITO DELLE QUERY ESEGUITE E LA DATA
	// DI ESECUZIONE
	private static void insertReport() {
		// CREAZIONE VETTORI DA INSERIRE NELL'INSERT DI JDBCTEMPLATE
		int[] types = new int[] { Types.INTEGER, Types.TIMESTAMP, Types.INTEGER };
		Object[] params = new Object[] { recordEstratti,
				// LibrerieVarie.getData(Constants.timestampPattern4)
				LocalDateTime.now(), count };

		Main.jdbcTemplate.update(
				"INSERT INTO sakila.reports (record_estratti, data_elaborazione, record_elaborati) VALUES (?, ?, ?)",
				params, types);
	}

	// METODO CHE ESEGUE LA QUERY
	private static List<BeanAttori> selectData(boolean test) {
		List<BeanAttori> output;
		BeanPropertyRowMapper<BeanAttori> rowMapper = new BeanPropertyRowMapper<BeanAttori>(BeanAttori.class);
		String sql = "SELECT actor_id, first_name ,last_name FROM sakila.actor WHERE last_name LIKE ('J%') and status = 0 ORDER BY actor_id DESC";

		output = Main.jdbcTemplate.query(sql, rowMapper);
		if (test) {
			int actorId = 0;
			if (output != null && output.size() > 0) {
				actorId = output.get(0).getActorId();
			} else {
				actorId = 0;
			}

			BeanAttori b1 = new BeanAttori(actorId + 1, "MARIO", "ROSSI");
			BeanAttori b2 = new BeanAttori(actorId + 2, "PAOLO", "BROSIO");
			output.add(b1);
			output.add(b2);
		}
		recordEstratti = output.size();
		System.out.println("Query Eseguita");
		System.out.println("Il numero di record estratti: " + recordEstratti);
		return output;
	}

	// METODO
	private static void writeFile(List<BeanAttori> output, String nomeFile, File file) {
		try {
			FileWriter w = new FileWriter(nomeFile);
			w.write("Id Attore" + SEPARATORE + "Nome attore" + SEPARATORE + "Cognome Attore \n");
			count = 0;
			countPeck = 0;
//			int[] types = new int[] { Types.VARCHAR };
			for (BeanAttori attore : output) {
				writeRecord(w, attore);
//				Object[] params = new Object[] { bean.getPolnum().toString() };
				// correggere actor_id
				Main.jdbcTemplate.update("UPDATE sakila.actor SET status = 1 where acto_id = " + attore.getActorId());
			}
			w.write(ACAPO + "Fine documento" + ACAPO);
			w.close();
			LibrerieFile.moveFile(file, "bk");
			file.delete();
			System.out.println("Record elaborati: " + count);
			System.out.println("Record elaborati Con PECK nel cognome: " + countPeck);
		} catch (Exception e) { // ERRORE SCRITTURA FILE
			LogError.logErrorToDatabase(e);
			
		}
	}

	// METODO DI CONTROLLO ESTRAZIONE RECORD
	private static void writeRecord(FileWriter w, BeanAttori attore) throws IOException {
		if (attore.getFirstName() != null && attore.getLastName() != null) {
			if (!attore.getLastName().equals("PECK")) {
				w.write(count + 1 + SEPARATORE + attore.getFirstName() + SEPARATORE + attore.getLastName() + ACAPO);
				count += 1;
			} else {
//				System.out.println("PECK");
				countPeck++;
			}
		} else {
			System.out.println("Record incompleto");
		}
	}
}

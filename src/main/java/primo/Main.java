package primo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import librerie.ConnessioneDB;
import librerie.LogError;

public class Main {
	public static DriverManagerDataSource driverManager;
	public static JdbcTemplate jdbcTemplate;
	public static String codFlow = null;
	public static String operation = null;
	public static String test = null;

	// METODO PER LA GESTIONE DEGLI ERRORI (INPUT PARAM e, ...)

	// CONTATORE DI RIGHE ELABORATE E CAMPI ESTRATTI

	public static void main(String[] args) throws Exception {

		readArgs(args);

		try {
			// simulazione di un'eccezione
			System.out.println("nel try");
			int result = 10 / 0;

		} catch (Exception e) {

			// funzione che manda l'eccezione al database
			LogError.logErrorToDatabase(e);
		}
		try {
			ConnessioneDB.setParametri();

//			if (operation.equals("CREA_FILE")) {
//				if (!test.equals("YES")) {
//					CreazioneFile.creazioneFile(false);
//				} else {
//					CreazioneFile.creazioneFile(true);
//				}
//			}
			if (operation.equals("CREA_FILE")) {
				CreazioneFile.creazioneFile(test.equals("YES") ? true : false);
			}

			if (operation.equals("CREA_FILE_TEST")) {
				CreazioneFile.creazioneFile(true);
			}
			
		} catch (Exception e) {
			// ERRORE CONNESSIONE DB
			LogError.logErrorToDatabase(e);
		}
	}

	private static void readArgs(String[] args) throws Exception {
		// ARGUMENTS
		String errMessage = "ERROR - Please, rerun jar with correct arguments: --codFlow COD_FLOW --operation OPERATION --testContext [true|false](optional - default:false) --reprocessFile [true|false](optional - default:false)";
		if (args.length % 2 == 1) {
			throw new Exception(errMessage);
		}
		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase("--codFlow")) {
				codFlow = args[i + 1];
			}
			if (args[i].equalsIgnoreCase("--operation")) {
				operation = args[i + 1];
			}
			if (args[i].equalsIgnoreCase("--test")) {
				test = args[i + 1];
			}
//			if (args[i].equalsIgnoreCase(GeneralConstants.paramPrefix + GeneralConstants.paramTestContext))
//				testContext = Boolean.valueOf(args[i + 1]);
//			if (args[i].equalsIgnoreCase(GeneralConstants.paramPrefix + GeneralConstants.paramReprocessFile))
//				reprocessFile = Boolean.valueOf(args[i + 1]);
//			if (args[i].equalsIgnoreCase(GeneralConstants.paramPrefix + GeneralConstants.paramMobu))
//				mobu = Boolean.valueOf(args[i + 1]);
//			if (args[i].equalsIgnoreCase(GeneralConstants.paramPrefix + "ricercaPerContraente"))
//				mobu = Boolean.valueOf(args[i + 1]);
		}
		// ERRORE CODFLOW/OPERATION NULLI
		if (codFlow == null || operation == null) {
			throw new Exception(errMessage);
		}
	}
	
	
	public static String getQueryFromDB(String idQuery) {
		String query = null;
		String sql = "SELECT * FROM queries";

		query = (String) Main.jdbcTemplate.queryForObject(sql, String.class);
		
		
		
		return null;
		// TODO: completa la richiesta delle query dal DB
		//		return query.replace();
		
	}
}

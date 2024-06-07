package librerie;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import costants.Constants;

public class LibrerieFile {

	// METODO PER LA GENERAZIONE DEL FILENAME E COPIA NEL PATH SCELTO
	public static boolean moveFile(File sourceFile, String targetPath) {

		File tDir = new File(targetPath);
		if (tDir.exists()) {
			String tmsStart = LibrerieVarie.getData(Constants.timestampPattern1);

			String newFilePath = targetPath + "\\" + sourceFile.getName().replace(".txt", "_" + tmsStart + ".txt");
			File movedFile = new File(newFilePath);
			if (movedFile.exists()) {
				movedFile.delete();
			}
			System.out.println("SPOSTAMENTO FILE IN " + newFilePath);
			return sourceFile.renameTo(new File(newFilePath));
		} else {
			System.out.println("Inserisci un altro percorso!!!");

			return false;
		}
	}
}

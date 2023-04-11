package controler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Random;


//import org.math.plot.Plot2DPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Orowan_Controler {
	private static Orowan_Controler instance = null;
	private String mainPath;
	
	private static boolean mode;

	public Orowan_Controler() {
		super();
		mode = true;
		mainPath = "C:\\Users\\bouta\\workspace_j2ee"; //to change depending on where you put the project
	}

	public static Orowan_Controler getInstance() {
		if (instance == null) {
			instance = new Orowan_Controler();
		}
		return instance;
	}

	
	public String getMainPath() {
		return mainPath;
	}

	public void setMainPath(String mainPath) {
		this.mainPath = mainPath;
	}

	public static boolean isMode() {
		return mode;
	}

	public static void setMode(boolean mode) {
		Orowan_Controler.mode = mode;
	}
	
	
	//***Function if the separator is Tabulation**//
	public List<String[]> readTableFromFileTab(String fileName) {
		List<String[]> table = new ArrayList<>();
		String filePath = mainPath+"\\Projet_ArcelorMittal\\src\\main\\data_orowan\\Krakov\\"+fileName;
		File file = new File(filePath);
		
		String separator = "\t";

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] row = line.split(separator);
				table.add(row);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return table;
	}
	

	//**Function if the separator is Semicolumn**//
	public List<String[]> readTableFromFileSemicolumn(String fileName) {
		List<String[]> table = new ArrayList<>();
		String filePath = mainPath+"\\Projet_ArcelorMittal\\src\\main\\data_orowan\\Krakov\\"+fileName;
		File file = new File(filePath);
		
		String separator = ";";

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] row = line.split(separator);
				table.add(row);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return table;
	}

	public List<String[]> extractColumns(List<String[]> table, int... columnIds) {
		List<String[]> extractedTable = new ArrayList<>();

		for (String[] row : table) {
			String[] extractedRow = new String[columnIds.length];
			for (int i = 0; i < columnIds.length; i++) {
				extractedRow[i] = row[columnIds[i]];
			}
			extractedTable.add(extractedRow);
		}

		return extractedTable;
	}
	
	public void writeTableToFile(String fileName, List<String[]> table, String[] columnNames) {
	    try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
	        // Add a first column containing the number "1" to the output table
	        for (int i = 0; i < table.size(); i++) {
	            String[] row = table.get(i);
	            String[] newRow = new String[columnNames.length];
	            newRow[0] = "1";
	            System.arraycopy(row, 0, newRow, 1, row.length);
	            table.set(i, newRow);
	        }

	        // Replace the first row of the input table with the given column names
	        String[] headerRow = Arrays.copyOf(table.get(0), columnNames.length);
	        System.arraycopy(columnNames, 0, headerRow, 0, columnNames.length);
	        table.set(0, headerRow);

	        // Write the modified table to the output file
			String separator = "\t";
	        for (String[] row : table) {
	            String line = String.join(separator, row);
	            bw.write(line);
	            bw.newLine();
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

    
	public void OrowanExe(String inputName)
	{
		try 
		  {
				String s = mainPath+"\\Projet_ArcelorMittal\\src\\main\\data_orowan\\Model\\Orowan_x64.exe";
				Process process = new ProcessBuilder(s).start(); //Orowan_x64
				
				OutputStream out = process.getOutputStream();
				s= (mode ? "i":"d") + "\nc\n"+mainPath+"\\Projet_ArcelorMittal\\src\\main\\data_orowan\\Model\\input_"+inputName+"\n"+mainPath+"\\Projet_ArcelorMittal\\src\\main\\data_orowan\\Model\\output_"+inputName+"\n"; //informations to pass to the Orowan_x64
				out.write(s.getBytes());
				out.flush();
				out.close();
		  }
		  catch (IOException e)
		  {
				e.printStackTrace();
		  }
	}
	
	public List<Double> readOutputFile(String output, int columnToPlot) {
		String filePath = mainPath+"\\Projet_ArcelorMittal\\src\\main\\data_orowan\\Model\\output_"+output;
	  

        // Read the file and extract the values from the specified column
        List<Double> values = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1) continue; // skip header
                String[] parts = line.split("\t");
                Double value = Double.parseDouble(parts[columnToPlot-1]);
                values.add(value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	    return values;
	}
	
	public void plotGraph(List<Double> values, String id) {
        // Create a line plot
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < values.size(); i++) {
            dataset.addValue(values.get(i), "Values", String.valueOf(i + 1));
        }
        JFreeChart chart = ChartFactory.createLineChart("Values", "Data Point", "Value", dataset, PlotOrientation.VERTICAL, false, true, false);
        try {
            ChartUtilities.saveChartAsPNG(new File(mainPath+"\\Projet_ArcelorMittal\\src\\main\\webapp\\plot_"+id+".png"), chart, 1000, 800);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    
    
}

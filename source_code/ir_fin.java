import java.util.Random;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Properties;

public class ir_fin {
	public static final String OUTPUT_FILEPATH = "IR";
	public static void main(String[] args) throws IOException {
		Properties prop = new Properties();
		InputStream input = null;

		input = new FileInputStream("app.config");
		prop.load(input);

		//get property values from .config file
		int users = Integer.parseInt(prop.getProperty("users"));
		int items = Integer.parseInt(prop.getProperty("items"));
		int dense = Integer.parseInt(prop.getProperty("dense"));
		int times = Integer.parseInt(prop.getProperty("times"));
		int k = Integer.parseInt(prop.getProperty("k"));
		//declare other variables
		int marks[][] = new int[users][items]; 
		int transmarks[][] = new int[items][users];
		double jaccard[][] = new double[users][users]; 
		double itemjaccard[][] = new double[items][items]; 
		double cosine[][] = new double[users][users]; 
		double itemcosine[][]= new double[items][items]; 
		double pearson[][] = new double[users][users]; 
		double itempearson[][] = new double[items][items];
		double mae[] = new double[users];
		double itemmae[] = new double[items];
		double maetotal;
		String filename;
		String toPrint;
		for (int t = 0; t < times; t++) {
			//generate random table
			marks = generateRandomMatrix(marks, dense);
			//System.out.println("original marks: ");
			//System.out.println(Arrays.deepToString(marks));//
			//end generate random table
			
			toPrint = dense+"% dense matrix: "+Arrays.deepToString(marks);
			
			//create jaccard table (user)
			jaccard = createJaccard(marks);
			//System.out.println("jaccard: ");//
			//System.out.println(Arrays.deepToString(jaccard));//
			//end create jaccard table
		
			//start error calculation for jaccard (user)
			maetotal = 0;
			mae = calculateErrors(marks, jaccard, k);
			//System.out.println("mae: ");
			//System.out.println(Arrays.toString(mae));
			for (int i = 0; i < mae.length; i++) {
				maetotal+= mae[i];
			}
			double a = (double) (maetotal/mae.length);
			maetotal=(double) Math.round(a * 100) / 100;
			//System.out.println("maetotal: "+maetotal);
			//end error calculation for jaccard (user)
			
			toPrint += "\r\n\r\nestimate errors (user to user)\r\njaccard:"+Arrays.toString(mae)+"\r\ntotal average: "+maetotal;
			
			//start create cosine table (user)
			cosine = createCosine(marks);
			//System.out.println("cosine: ");
			//System.out.println(Arrays.deepToString(cosine));
			//end create cosine table (user)
			
			//start error calculation for cosine (user)
			maetotal = 0;
			mae = calculateErrors(marks, cosine, k);
			//System.out.println("mae: ");
			//System.out.println(Arrays.toString(mae));
			for (int i = 0; i < mae.length; i++) {
				maetotal+= mae[i];
			}
			a = (double) (maetotal/mae.length);
			maetotal=(double) Math.round(a * 100) / 100;
			//System.out.println("maetotal: "+maetotal);
			//end error calculation for cosine (user)
			
			toPrint += "\r\ncosine:"+Arrays.toString(mae)+"\r\ntotal average: "+maetotal;
			
			//start create pearson table (user)
			pearson = createPearson(marks);
			//System.out.println("pearson: ");
			//System.out.println(Arrays.deepToString(pearson));
			//end create pearson table (user)
			
			//start error calculation for pearson (user)
			maetotal = 0;
			mae = calculateErrors(marks, pearson, k);
			//System.out.println("mae: ");
			//System.out.println(Arrays.toString(mae));
			for (int i = 0; i < mae.length; i++) {
				maetotal+= mae[i];
			}
			a = (double) (maetotal/mae.length);
			maetotal=(double) Math.round(a * 100) / 100;
			//System.out.println("maetotal: "+maetotal);
			//end error calculation for pearson (user)
			
			toPrint += "\r\npearson:"+Arrays.toString(mae)+"\r\ntotal average: "+maetotal;
			
			//start transpose marks
			transmarks = transposeMatrix(marks);
			//System.out.println("transmarks: ");
			//System.out.println(Arrays.deepToString(transmarks));
			//end transpose marks
			
			//create jaccard table (item)
			itemjaccard = createJaccard(transmarks);
			//System.out.println("item jaccard: ");
			//System.out.println(Arrays.deepToString(itemjaccard));//
			//end create jaccard table
			
			//start error calculation for jaccard (item)
			maetotal = 0;
			itemmae = calculateErrors(transmarks, itemjaccard, k);
			//System.out.println("mae: ");
			//System.out.println(Arrays.toString(itemmae));
			for (int i = 0; i < itemmae.length; i++) {
				maetotal+= itemmae[i];
			}
			a = (double) (maetotal/itemmae.length);
			maetotal=(double) Math.round(a * 100) / 100;
			//System.out.println("maetotal: "+maetotal);
			//end error calculation for jaccard (item)
			
			toPrint += "\r\n\r\nestimate errors (item to item)\r\njaccard:"+Arrays.toString(itemmae)+"\r\ntotal average: "+maetotal;
			
			//start create cosine table (item)
			itemcosine = createCosine(transmarks);
			//System.out.println("cosine: ");
			//System.out.println(Arrays.deepToString(itemcosine));
			//end create cosine table (item)
			
			//start error calculation for cosine (item)
			maetotal = 0;
			itemmae = calculateErrors(transmarks, itemcosine, k);
			//System.out.println("mae: ");
			//System.out.println(Arrays.toString(itemmae));
			for (int i = 0; i < itemmae.length; i++) {
				maetotal+= itemmae[i];
			}
			a = (double) (maetotal/itemmae.length);
			maetotal=(double) Math.round(a * 100) / 100;
			//System.out.println("maetotal: "+maetotal);
			//end error calculation for cosine (item)
			
			toPrint += "\r\ncosine:"+Arrays.toString(itemmae)+"\r\ntotal average: "+maetotal;
			
			//start create pearson table (item)
			itempearson = createPearson(transmarks);
			//System.out.println("pearson: ");
			//System.out.println(Arrays.deepToString(itempearson));
			//end create pearson table (item)
			
			//start error calculation for pearson (item)
			maetotal = 0;
			itemmae = calculateErrors(transmarks, itempearson, k);
			//System.out.println("mae: ");
			//System.out.println(Arrays.toString(itemmae));
			for (int i = 0; i < itemmae.length; i++) {
				maetotal+= itemmae[i];
			}
			a = (double) (maetotal/itemmae.length);
			maetotal=(double) Math.round(a * 100) / 100;
			//System.out.println("maetotal: "+maetotal);
			//end error calculation for pearson (item)
			
			toPrint += "\r\npearson:"+Arrays.toString(itemmae)+"\r\ntotal average: "+maetotal;
			
			filename = OUTPUT_FILEPATH+"_iteration_"+(t+1)+".txt";
			writeFile(filename, toPrint);
		}
	}
	public static int[][] generateRandomMatrix(int marks[][], int dense) {
		Random rand = new Random();
		for (int i = 0; i < marks.length; i++) {
			for (int j = 0; j < marks[0].length; j++) {
				int  n = rand.nextInt(100) + 1;
				if (n>=dense) marks[i][j] = 0;
				else {
					int randomMark = (int) Math.round(rand.nextGaussian()*1+3);
					marks[i][j] = randomMark;
				}
				//System.out.println("["+i+"]"+"["+j+"]"+n+" mark: "+marks[i][j]);
			}
		}
		return marks;
	}
	public static double[][] createJaccard(int marks[][]) {
		double jaccard[][] = new double[marks.length][marks.length]; 
		int j_inter = 0;
		int j_union = 0;
		for (int i = 0; i < marks.length; i++) {
			for (int l = 0; l < marks.length; l++) {
				for (int j = 0; j < marks[0].length; j++) {
					if ((marks[i][j]!=0)||(marks[l][j]!=0)) j_union++;
					if ((marks[i][j]!=0)&&(marks[l][j]!=0)) j_inter++;
					double a = (double) j_inter/j_union;
					jaccard[i][l] = (double) Math.round(a * 100) / 100;
					//System.out.println(j_inter);
	    			//System.out.println(j_union);
				}
				j_inter = 0;
				j_union = 0;
			}
		}
		//System.out.println(Arrays.deepToString(jaccard));//
		return jaccard;
	}
	public static double[][] createCosine(int marks[][]) {
		double cosine[][] = new double[marks.length][marks.length]; 
		double dotProduct = 0;
		double normA = 0;
		double normB = 0;
		for (int i = 0; i < marks.length; i++) {
			for (int l = 0; l < marks.length; l++) {
				for (int j = 0; j < marks[0].length; j++) {
					dotProduct += marks[i][j] * marks[l][j];
					normA += Math.pow(marks[i][j], 2);
				    normB += Math.pow(marks[l][j], 2);
				}
				double a = (double) dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
				cosine[i][l] = (double) Math.round(a * 100) / 100;
				//System.out.println("("+i+", "+l+")"+" cosine: "+cosine[i][l]);
				dotProduct = 0;
				normA = 0;
				normB = 0;
			}
		}
		return cosine;
	}
	public static double[][] createPearson(int marks[][]) {
		double pearson[][] = new double[marks.length][marks.length]; 
	    double A_mean;
	    int A_count;
	    double B_mean;
	    int B_count;
	    double cov;
	    double A_sd;
	    double B_sd;
	    for (int i = 0; i < marks.length; i++) {
			for (int l = 0; l < marks.length; l++) {
				A_mean = 0;
			    B_mean = 0;
			    cov = 0;
			    A_sd = 0;
			    B_sd = 0;
			    A_count = 0;
			    B_count = 0;
				for (int j = 0; j < marks[0].length; j++) {
					if (marks[i][j]!=0) {
						A_mean += marks[i][j];
						A_count++;
					}
					if (marks[l][j]!=0) {
						B_mean += marks[l][j];
						B_count++;
					}
				}
				A_mean = A_mean / A_count;
				A_mean = (double) Math.round(A_mean * 100) / 100;
				B_mean = B_mean / B_count;
				B_mean = (double) Math.round(B_mean * 100) / 100;
				//System.out.println("A mean:"+A_mean);//
				//System.out.println("B mean:"+B_mean);//
				for (int j = 0; j < marks[0].length; j++) {
					if ((marks[i][j]!=0)&&(marks[l][j]!=0)) {
						cov += (marks[i][j]-A_mean)*(marks[l][j]-B_mean);
						A_sd += Math.pow((marks[i][j]-A_mean), 2);
						B_sd += Math.pow((marks[l][j]-B_mean), 2);
					}
				}
				A_sd = Math.sqrt(A_sd);
				B_sd = Math.sqrt(B_sd);
				//System.out.println("A sd:"+A_sd);
				//System.out.println("B sd:"+B_sd);
				//System.out.println("A_sd*B_sd: "+(A_sd*B_sd));
				//System.out.println("["+i+"]"+"["+l+"]"+" pearson: "+(cov/(A_sd*B_sd)));//
				double a = (double) cov/(A_sd*B_sd);
				pearson[i][l] = (double) Math.round(a * 100) / 100;
			}
		}
	    return pearson;
	}
	public static int[][] sortNeighbours(double similarity[][]) {
		double[]temp = new double[similarity.length];   
	    int[] index = new int[similarity.length];
	    int[][] places = new int[similarity.length][similarity.length];
	    for (int i = 0; i < similarity.length; i++) {
	    	for (int j = 0; j < similarity.length; j++) {
	            temp[j] = similarity[i][j];
	            index[j] = j;
	    	}
	    	for (int l = 1; l < similarity.length; l++) {
	    		if(temp[l] > temp[l - 1] ) {
	    			temp[l] = temp[l] + temp[l - 1];
	                temp[l - 1] = temp[l] - temp[l - 1];
	                temp[l] = temp[l] - temp[l - 1];
	                //
	                index[l] = index[l] + index[l - 1];
	                index[l - 1] = index[l] - index[l - 1];
	                index[l] = index[l] - index[l - 1];
	                l=0;
	    		}
	    	}
	        //System.out.println(Arrays.toString(temp));
	        //System.out.println(Arrays.toString(index));
	        for (int j = 0; j < similarity.length; j++) {
	        	places[i][j] = index[j];
	        }
	    }
	    //System.out.println(Arrays.deepToString(places));//
	    return places;
	}
	public static int[][] fillZeros(int marks[][], double similarity[][], int places[][], int k) {
		int estimates[][] = new int[marks.length][marks[0].length]; 
		//estimates = marks;
		for (int i = 0; i < marks.length; i++) {
			for (int j = 0; j < marks[0].length; j++) {
				estimates[i][j] = marks[i][j];
			}
		}
		for (int i = 0; i < marks.length; i++) {
	    	for (int j = 0; j < marks[0].length; j++) {
	    		if (marks[i][j]==0) {
	    			int counter = 0;
	    			int valid = 0;
	    			double sum = 0;
	    			for (int l = 0; l < marks.length; l++) {
	    				if (i==l) l++;
	    				//System.out.println("kplace: "+places[i][l]);//
	    				if (marks[places[i][l]][j]!=0) {
	    					sum +=marks[places[i][l]][j]*similarity[i][places[i][l]];
	    					//System.out.println("sum: "+sum+"("+i+", "+places[i][l]+") for object "+j);//
		    				valid++;
	    				}
	    				counter++; 
	    				if (counter==k) break;
	    			}
	    			estimates[i][j] = (int) Math.round(sum/valid);
	    			//System.out.println("double: "+(sum/valid));
	    			//System.out.println("mark: "+estimates[i][j]);
	    		}
	    	}
	    }
	    //System.out.println(Arrays.deepToString(estimates));
		return estimates;
	}
	public static double[][] generateEstimates(int filledmarks[][], double similarity[][], int places[][], int k) {
		double estimates[][] = new double[filledmarks.length][filledmarks[0].length]; 
		for (int i = 0; i < filledmarks.length; i++) {
	    	for (int j = 0; j < filledmarks[0].length; j++) {
	    			int counter = 0;
	    			int valid = 0;
	    			double sum = 0;
	    			for (int l = 0; l < filledmarks.length; l++) {
	    				if (i==places[i][l]) l++;
	    				//System.out.println("kplace: "+places[i][l]);
	    				if (filledmarks[places[i][l]][j]!=0) {
	    					sum +=filledmarks[places[i][l]][j]*similarity[i][places[i][l]];
	    					//System.out.println("sum: "+sum+"("+i+", "+places[i][l]+") for object "+j);
		    				valid++;
	    				}
	    				counter++; 
	    				if (counter==k) break;
	    			}
	    			double a = (double) (sum/valid);
	    			estimates[i][j] = (double) Math.round(a * 100) / 100;
	    			//System.out.println("double: "+(sum/valid));
	    			//System.out.println("estimates: "+estimates[i][j]);
	    	}
	    }
	    //System.out.println(Arrays.deepToString(estimates));
		return estimates;
	}
	public static double[] calculateMAE(int filledmarks[][], double estimates[][]) {
		double mae[] = new double [filledmarks.length];	
		for (int i = 0; i < filledmarks.length; i++) {
			double sum = 0;
			int counter = 0;
			for (int j = 0; j < filledmarks[0].length; j++) {
				if (filledmarks[i][j]!=0) {
					sum += Math.abs(filledmarks[i][j] - estimates[i][j]);
					counter++; 
				}
				double a = (double) (sum/counter);
				mae[i]= (double) Math.round(a * 100) / 100;
			}
		}
		//System.out.println(Arrays.toString(mae));
		return mae;
		
	}
	public static void writeFile (String fileName, String toPrint) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		writer.println(toPrint);
		writer.close();
	}
	public static int[][] transposeMatrix(int marks[][]) {
		int transmarks[][] = new int [marks[0].length][marks.length];
		for(int i=0;i<marks[0].length;i++){
			for (int j = 0; j < marks.length; j++) {
				transmarks[i][j] = marks[j][i];
			}
		}
		//System.out.println(Arrays.toString(transmarks));//
		return transmarks;
	}
	public static double[] calculateErrors (int marks[][], double similarity[][], int k) {
		int places[][]= new int[marks.length][marks.length];
		int filledmarks[][] = new int[marks.length][marks[0].length]; 
		double estimates[][] = new double[marks.length][marks[0].length];
		double mae[] = new double[marks.length];
		//find k nearest neighbours
		places = sortNeighbours(similarity);
		//System.out.println("places: ");//
		//System.out.println(Arrays.deepToString(places));//
		//calculate empty values from k nearest neighbours
		filledmarks = fillZeros(marks, similarity, places, k);
		//System.out.println("filled marks: ");//
		//System.out.println(Arrays.deepToString(filledmarks));//
		//calculate estimates from filled matrix
		estimates = generateEstimates(filledmarks, similarity, places, k);
		//System.out.println("estimates: ");//
		//System.out.println(Arrays.deepToString(estimates));//
		//calculate mean absolute error
		mae = calculateMAE(filledmarks, estimates);
		return mae;
	}

}

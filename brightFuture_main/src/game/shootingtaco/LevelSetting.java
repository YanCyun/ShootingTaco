package game.shootingtaco;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.badlogic.gdx.Gdx;


public class LevelSetting {
	
	public final static String file = ".shootingtaco";
	public final static int levelCount = 11;
	
	
	public static int[] levelStar = new int[levelCount];
	public static int[] levelLock = new int[levelCount];
	public static boolean voice;
	
	public static void load(){
		BufferedReader in = null;
		try{
			in = new BufferedReader(new InputStreamReader(Gdx.files.external(file).read()));
			voice = Boolean.parseBoolean(in.readLine());
			for(int i = 0 ; i < levelCount ; i++){
				levelLock[i] = Integer.parseInt(in.readLine());
				levelStar[i] = Integer.parseInt(in.readLine());
				System.out.print("Star:"+levelStar[i]);
			}
		}catch(Throwable e){	
			System.out.println("LevelSetting Throwable!!   " + e);
		}
		finally{
			System.out.println("LevelSetting!!");
			levelLock[0] = 1;
			try {
				if (in != null) in.close();
			} catch (IOException e) {
			}
		}
		
	}
	public static void save(){
		BufferedWriter out = null;
		try{
			out = new BufferedWriter(new OutputStreamWriter(Gdx.files.external(file).write(false)));
			out.write(Boolean.toString(voice)+"\n");
			for(int i = 0 ; i < levelCount ; i++){
				out.write(Integer.toString(levelLock[i])+"\n");
				out.write(Integer.toString(levelStar[i])+"\n");
			}
		}catch(Throwable e){	
			System.out.println("LevelSetting Throwable!!   " + e);
		}
		finally{
			try{
				if(out != null) out.close();
			}catch(IOException e){
			}
		}
		
	}
	
	public static void levelUnlock(int level){
		levelLock[level-1] = 1; 
	}
	
	public static void setLevelStar(int level , int star){
		levelStar[level-1] = star;
	}
}

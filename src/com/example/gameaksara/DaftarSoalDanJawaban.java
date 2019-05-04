package com.example.gameaksara;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class DaftarSoalDanJawaban {
	private String[] soal = new String[50];
	private String[] jawaban = new String[50];
	private StringTokenizer split;
	
	public void setDaftarJawaban(String jawaban, int index){
		String s = String.format(jawaban);
		this.jawaban[index] = s;
	}
	
	public void setDaftarSoal(String soal, int index){
		String s = String.format(soal);
		this.soal[index] = s;
	}
	
	public String[] getDaftarSoal(){
		return soal;
	}
	
	public String[] getDaftarJawaban(){
		return jawaban;
	}
	
	public boolean cekJawaban(String isSoal, String isJawaban){
		boolean isTrue = false;
		for(int i=0; i<50; i++){
			String compareSoal = this.soal[i];
			String compareJawaban = this.jawaban[i];
			if(isSoal.equals(compareSoal) && isJawaban.equals(compareJawaban)){
				isTrue = true;
				break;
			}else{
				isTrue = false;
			}
		}
		return isTrue;
	}
}

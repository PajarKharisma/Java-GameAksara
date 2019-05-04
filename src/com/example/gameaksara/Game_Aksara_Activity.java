package com.example.gameaksara;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Game_Aksara_Activity extends SimpleBaseGameActivity {

	//KONSTANTA
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	private static final int COUNT_SOAL = 49;
	private VertexBufferObjectManager vbo;
	private static final int TOP_KEYOARD = 187; 
	private static final int MID_KEYOARD = 112;
	private static final int BOTTOM_KEYOARD = 38;
	private static final int SKOR_MAX = 50;
	
	//DEKLARASI DAFTAR SOAL DAN JAWABAN
	private DaftarSoalDanJawaban daftarSoalDanJawaban = new DaftarSoalDanJawaban();
	private String[] daftarSoal = daftarSoalDanJawaban.getDaftarSoal();
	private String[] daftarJawaban = daftarSoalDanJawaban.getDaftarJawaban();
	
	//DEKLARASI SHARED PREFERENCE
	SharedPreferences sp;
	Editor ed;
	
	//DEKLARASI METODE LCM
	private int konsA = 1;
	private int konsC = 9;
	private int hasilLcm = (int) (Math.random()*COUNT_SOAL);
	private int limitMax = COUNT_SOAL;
	private int x0 = hasilLcm;
	
	//DEKLARASI AWAL
	private Scene sceneMenu, sceneBantuan, sceneMain, sceneSoal, sceneKalah, sceneInputNama, sceneDaftarSkor, sceneDialogBox, sceneSkorTertinggi;
	private Camera camera;
	
	//DEKLARASI FONT DAN TEXT
	private Font fontAvg, fontKesempatan, fontNilaiSkor;
	private Text textSoal, textJawabA, textJawabB, textJawabC, textJawabD, textKesempatan, textInputNama, textNilaiSkor, textNamaSkor;
	
	//DEKLARASI ANIMASI
	private TiledTextureRegion regionAnimateGinko;
	private AnimatedSprite animateSpriteGinko;
	
	//DEKLARASI RESOURCE MENU UTAMA
	private SpriteBackground spriteBackgroundMenu;
	private TextureRegion regionBtnMulai, regionBtnBantuan, regionBtnSkor, regionBtnKeluar;
	private Sprite spriteBtnMulai, spriteBtnBantuan, spriteBtnSkor, spriteBtnKeluar;
	private Entity groupMenuUtama;
	
	//DEKLARASI RESOURCE MENU BANTUAN
	private SpriteBackground spriteBackgroundBantuan;
	private TextureRegion regionBtnKembali;
	private Sprite spriteBtnKembali;
	
	//DEKLARASI RESOURCE MENU SKOR
	private SpriteBackground spriteBackgroundDaftarSkor;
	private Sprite spriteBtnKembaliSkor;
	private Font fontDaftarSkor;
	private Text textSaveNamaNilai1, textSaveNamaNilai2, textSaveNamaNilai3;
	
	//DEKLARASI RESOURCE MENU MAIN
	private SpriteBackground spriteBackgroundMain;
	private TextureRegion regionBgSoal, regionBtnJwbA, regionBtnJwbB, regionBtnJwbC, regionBtnJwbD, regionBtnKembaliHome, regionFalseMark, regionTrueMark;
	private Sprite spriteBgSoal, spriteBtnJwbA, spriteBtnJwbB, spriteBtnJwbC, spriteBtnJwbD, spriteBtnKembaliHome, spriteFalseMarkA, spriteFalseMarkB, spriteFalseMarkC, spriteFalseMarkD, spriteTrueMark;
	private Entity groupMenuMain;
	private int kesempatan = 3;
	private int nilaiSkor = 0;
	private BitmapTextureAtlas textureGambarDiSoal;
	private TextureRegion regionGambarDiSoal;
	private Sprite spriteGambarDiSoal;
	
	//DEKLARASI RESOURCE SOAL
	private BitmapTextureAtlas textureSoal;
	private TextureRegion regionSoal, regionKalah;
	private Sprite spriteSoal, spriteKalah;
	
	//DEKLARASI RESOURCE SOUND
	private Sound soundKalah, soundMenang;
	private Sound[] soundSoalBenar = new Sound[50];
	private Music musicBacksound;
	
	//DEKLARASI KEYBOARD
	private String saveNama;
	private String inputNama = "";
	private TextureRegion regionBtnSaveNama;
	private TextureRegion regionBtnClr;
	private Sprite spriteBtnClr;
	private BitmapTextureAtlas[] textureKeyboard = new BitmapTextureAtlas[30];
	private TextureRegion[] regionKeyboard = new TextureRegion[30];
	private Sprite[] spriteKeyboard = new Sprite[30];
	private SpriteBackground spriteBackgroundInputNama;
	private Sprite spriteBtnSaveNama;
	private Entity groupSceneKeyboard;
	
	//DEKLARASI DIALOG BOX
	private TextureRegion regionDbYes, regionDbNo, regionDbQues, regionDbBack;
	private Sprite spriteDbYes, spriteDbNo, spriteDbQues, spriteDbBack;
	private Entity groupDialogBox;
	
	//DEKLARASI SKOR TERTINGGI
	private Sprite spriteTextSkorTertinggi, spriteBtnKembaliSkorTertinggi;
	private SpriteBackground spriteBackgroundSkorTertinggi;
	private TextureRegion regionTextSkorTertinggi;
	
	//DEKLARASI CEK JAWABAN
	String isSoal;
	String isJawabanA = "";
	String isJawabanB = "";
	String isJawabanC = "";
	String isJawabanD = "";
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions en = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		en.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		en.getAudioOptions().setNeedsSound(true); //pakai musik atau tidak
		en.getAudioOptions().setNeedsMusic(true);
		return en;
	}

	@Override
	protected void onCreateResources() throws IOException {
		//RESOURCE FONT
		ITexture textureFont = new BitmapTextureAtlas(getTextureManager(), 480, 480, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mEngine.getTextureManager().loadTexture(textureFont);
		fontAvg = FontFactory.createFromAsset(getFontManager(), textureFont, getAssets(), "font/avg.ttf", 28, true, android.graphics.Color.WHITE);
		mEngine.getFontManager().loadFont(fontAvg);
		
		ITexture textureFontKesempatan = new BitmapTextureAtlas(getTextureManager(), 480, 480, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mEngine.getTextureManager().loadTexture(textureFontKesempatan);
		fontKesempatan = FontFactory.createFromAsset(getFontManager(), textureFontKesempatan, getAssets(), "font/avg.ttf", 48, true, android.graphics.Color.BLACK);
		mEngine.getFontManager().loadFont(fontKesempatan);
		
		ITexture textureFontNilaiSkor = new BitmapTextureAtlas(getTextureManager(), 480, 480, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mEngine.getTextureManager().loadTexture(textureFontNilaiSkor);
		fontNilaiSkor = FontFactory.createFromAsset(getFontManager(), textureFontNilaiSkor, getAssets(), "font/avg.ttf", 200, true, android.graphics.Color.RED);
		mEngine.getFontManager().loadFont(fontNilaiSkor);
		
		ITexture textureFontDaftarSkor = new BitmapTextureAtlas(getTextureManager(), 480, 480, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mEngine.getTextureManager().loadTexture(textureFontDaftarSkor);
		fontDaftarSkor = FontFactory.createFromAsset(getFontManager(), textureFontDaftarSkor, getAssets(), "font/cc.ttf", 48, true, android.graphics.Color.BLACK);
		mEngine.getFontManager().loadFont(fontDaftarSkor);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//RESOURCE ANIMASI
		BitmapTextureAtlas textureAnimateGinko = new BitmapTextureAtlas(getTextureManager(), CAMERA_WIDTH*3, CAMERA_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionAnimateGinko = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(textureAnimateGinko, this, "gfx/Animasi/animasi.png", 0, 0, 3, 1);
		mEngine.getTextureManager().loadTexture(textureAnimateGinko);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//RESOURCE MENU UTAMA
		BitmapTextureAtlas textureBackground = new BitmapTextureAtlas(getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TextureRegion regionBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBackground, getAssets(), "gfx/MenuAwal/background.jpg", 0, 0);
		mEngine.getTextureManager().loadTexture(textureBackground);
		Sprite spriteBg = new Sprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, regionBackground, vbo);
		spriteBackgroundMenu = new SpriteBackground(spriteBg);
		
		BitmapTextureAtlas textureBtnMulai = new BitmapTextureAtlas(getTextureManager(), 360, 360, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionBtnMulai = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBtnMulai, getAssets(), "gfx/MenuAwal/btn_mulai.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureBtnMulai);
		
		BitmapTextureAtlas textureBtnBantuan = new BitmapTextureAtlas(getTextureManager(), 360, 360, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionBtnBantuan = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBtnBantuan, getAssets(), "gfx/MenuAwal/btn_bantuan.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureBtnBantuan);
		
		BitmapTextureAtlas textureBtnSkor = new BitmapTextureAtlas(getTextureManager(), 360, 360, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionBtnSkor = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBtnSkor, getAssets(), "gfx/MenuAwal/btn_skor.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureBtnSkor);
		
		BitmapTextureAtlas textureBtnKeluar = new BitmapTextureAtlas(getTextureManager(), 360, 360, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionBtnKeluar = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBtnKeluar, getAssets(), "gfx/MenuAwal/keluar.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureBtnKeluar);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//RESOURCE MENU BANTUAN
		BitmapTextureAtlas textureBackgroundBantuan = new BitmapTextureAtlas(getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TextureRegion regionBackgroundBantuan = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBackgroundBantuan, getAssets(), "gfx/Bantuan/backgroundBantuan.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureBackgroundBantuan);
		Sprite spriteBgBantuan = new Sprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, regionBackgroundBantuan, vbo);
		spriteBackgroundBantuan = new SpriteBackground(spriteBgBantuan);
		
		BitmapTextureAtlas textureBtnKembali = new BitmapTextureAtlas(getTextureManager(), 360, 360, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionBtnKembali = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBtnKembali, getAssets(), "gfx/Bantuan/btn_kembali.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureBtnKembali);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//RESOURCE MENU SKOR
		BitmapTextureAtlas textureBackgroundSkor = new BitmapTextureAtlas(getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TextureRegion regionBackgroundSkor = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBackgroundSkor, getAssets(), "gfx/SkorTertinggi/backgroundSkorTertinggi.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureBackgroundSkor);
		Sprite SpriteBgSkor = new Sprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, regionBackgroundSkor, vbo);
		spriteBackgroundDaftarSkor = new SpriteBackground(SpriteBgSkor);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//RESOURCE MENU MAIN
		BitmapTextureAtlas textureBackgroundMain = new BitmapTextureAtlas(getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TextureRegion regionBackgroundMain = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBackgroundMain, getAssets(), "gfx/Main/backgroundMain.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureBackgroundMain);
		Sprite spriteBgMain = new Sprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, regionBackgroundMain, vbo);
		spriteBackgroundMain = new SpriteBackground(spriteBgMain);
		
		BitmapTextureAtlas textureBgSoal = new BitmapTextureAtlas(getTextureManager(), 480, 480, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionBgSoal = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBgSoal, getAssets(), "gfx/Main/bgSoal.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureBgSoal);
		
		BitmapTextureAtlas textureBtnJwbA = new BitmapTextureAtlas(getTextureManager(), 360, 360, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionBtnJwbA = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBtnJwbA, getAssets(), "gfx/Main/btnJawabA.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureBtnJwbA);
		
		BitmapTextureAtlas textureBtnJwbB = new BitmapTextureAtlas(getTextureManager(), 360, 360, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionBtnJwbB = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBtnJwbB, getAssets(), "gfx/Main/btnJawabB.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureBtnJwbB);
		
		BitmapTextureAtlas textureBtnJwbC = new BitmapTextureAtlas(getTextureManager(), 360, 360, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionBtnJwbC = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBtnJwbC, getAssets(), "gfx/Main/btnJawabC.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureBtnJwbC);
		
		BitmapTextureAtlas textureBtnJwbD = new BitmapTextureAtlas(getTextureManager(), 360, 360, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionBtnJwbD = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBtnJwbD, getAssets(), "gfx/Main/btnJawabD.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureBtnJwbD);
		
		BitmapTextureAtlas textureBtnKembaliHome = new BitmapTextureAtlas(getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionBtnKembaliHome = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBtnKembaliHome, getAssets(), "gfx/Main/btnMainHome.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureBtnKembaliHome);
		
		textureGambarDiSoal = new BitmapTextureAtlas(getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionGambarDiSoal = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureGambarDiSoal, getAssets(), "gfx/gambarDiSoal/soal1.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureGambarDiSoal);
		
		BitmapTextureAtlas textureFalseMark = new BitmapTextureAtlas(getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionFalseMark = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureFalseMark, getAssets(), "gfx/Main/falseMark.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureFalseMark);
		
		BitmapTextureAtlas textureTrueMark = new BitmapTextureAtlas(getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionTrueMark = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureTrueMark, getAssets(), "gfx/Main/trueMark.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureTrueMark);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//RESOURCE SOAL SELANJUTNYA
		textureSoal = new BitmapTextureAtlas(getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionSoal = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureSoal, getAssets(), "gfx/Soal/Soal1.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureSoal);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//RESOURCE KALAH
		BitmapTextureAtlas textureKalah = new BitmapTextureAtlas(getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionKalah = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureKalah, getAssets(), "gfx/Soal/kalah.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureKalah);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//RESOURCE DIALOG BOX
		BitmapTextureAtlas textureDbQues = new BitmapTextureAtlas(getTextureManager(), 480, 480, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionDbQues = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureDbQues, getAssets(), "gfx/DialogBox/dbQuestion.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureDbQues);
		
		BitmapTextureAtlas textureDbYes = new BitmapTextureAtlas(getTextureManager(), 300, 300, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionDbYes = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureDbYes, getAssets(), "gfx/DialogBox/dbYes.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureDbYes);
		
		BitmapTextureAtlas textureDbNo = new BitmapTextureAtlas(getTextureManager(), 300, 300, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionDbNo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureDbNo, getAssets(), "gfx/DialogBox/dbNo.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureDbNo);
		
		BitmapTextureAtlas textureDbBack = new BitmapTextureAtlas(getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionDbBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureDbBack, getAssets(), "gfx/DialogBox/dbBack.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureDbBack);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//RESOURCE SKOR TERTINGGI
		BitmapTextureAtlas textureBackgroundSkorTertinggi = new BitmapTextureAtlas(getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TextureRegion regionBackgroundSkorTertinggi = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBackgroundSkorTertinggi, getAssets(), "gfx/SelamatSkor/background.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureBackgroundSkorTertinggi);
		Sprite spriteBgSkorTertinggi = new Sprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, regionBackgroundSkorTertinggi, vbo);
		spriteBackgroundSkorTertinggi = new SpriteBackground(spriteBgSkorTertinggi);
		
		BitmapTextureAtlas textureTextSkorTertinggi = new BitmapTextureAtlas(getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionTextSkorTertinggi = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureTextSkorTertinggi, getAssets(), "gfx/SelamatSkor/text.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureTextSkorTertinggi);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//RESOURCE SOUND
		try{
			soundKalah = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(), "sfx/lose.mp3");
			soundMenang = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(), "sfx/soundMenang.mp3");
			musicBacksound = MusicFactory.createMusicFromAsset(this.getMusicManager(), this.getApplicationContext(), "sfx/backsound.mp3");
			musicBacksound.setLooping(true);
		  } catch (IOException e){
			  e.printStackTrace();
		  }
		
		for(int i=0; i<50; i++){
			try {
				soundSoalBenar[i] = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(), "sfx/sound"+(i+1)+".mp3");
			} catch (IOException e) {
				 e.printStackTrace();
			}
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//RESOURCE KEYBOARD
		for(int i=1; i<27; i++){
			textureKeyboard[i-1] = new BitmapTextureAtlas(getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			regionKeyboard[i-1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureKeyboard[i-1], getAssets(), "gfx/InputNama/btn"+i+".png", 0, 0);
			mEngine.getTextureManager().loadTexture(textureKeyboard[i-1]);
		}
		
		BitmapTextureAtlas textureBackgroundInputNama = new BitmapTextureAtlas(getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TextureRegion regionBackgroundInputNama = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBackgroundInputNama, getAssets(), "gfx/InputNama/backgroundInputNama.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureBackgroundInputNama);
		Sprite spriteBgInputNama = new Sprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, regionBackgroundInputNama, vbo);
		spriteBackgroundInputNama = new SpriteBackground(spriteBgInputNama);
		
		BitmapTextureAtlas textureInputNama = new BitmapTextureAtlas(getTextureManager(), 400, 400, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionBtnSaveNama = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureInputNama, getAssets(), "gfx/InputNama/btnSimpan.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureInputNama);
		
		BitmapTextureAtlas textureBtnClr = new BitmapTextureAtlas(getTextureManager(), 350, 350, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regionBtnClr = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBtnClr, getAssets(), "gfx/InputNama/btnClr.png", 0, 0);
		mEngine.getTextureManager().loadTexture(textureBtnClr);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	}

	@Override
	protected Scene onCreateScene() {
		sceneMenu = new Scene();
		sceneBantuan = new Scene();
		sceneMain = new Scene();
		sceneSoal = new Scene();
		sceneKalah = new Scene();
		sceneInputNama = new Scene();
		sceneDaftarSkor = new Scene();
		sceneDialogBox = new Scene();
		sceneSkorTertinggi = new Scene();
		
		sp = getPreferences(MODE_PRIVATE);
		ed = sp.edit();
		
		try {
			initSoaldanJawaban();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		musicBacksound.play();
		touchEvent();
		menuStart();
		menuBantuan();
		menuMain();
		menuSoal();
		alertKalah();
		initInputNama();
		initMenuSkor();
		initDialogBox();
		initSceneSkorTertinggi();
		
		return sceneMenu;
	}
	
	private void initSoaldanJawaban() throws IOException{
		BufferedReader brJawaban = new BufferedReader(new InputStreamReader(getAssets().open("soalDanJawaban/jawaban.txt")));
		BufferedReader brSoal = new BufferedReader(new InputStreamReader(getAssets().open("soalDanJawaban/soal.txt")));
		String textJawaban;
		String textSoal;
		
		int iterasiJawaban=0;
		while ((textJawaban = brJawaban.readLine()) != null){
			daftarSoalDanJawaban.setDaftarJawaban(textJawaban, iterasiJawaban);
			iterasiJawaban++;
		}
		
		int iterasiSoal=0;
		while ((textSoal= brSoal.readLine()) != null){
			daftarSoalDanJawaban.setDaftarSoal(textSoal, iterasiSoal);
			iterasiSoal++;
		}
		//daftarSoal = daftarSoalDanJawaban.getDaftarSoal();
		//daftarJawaban = daftarSoalDanJawaban.getDaftarJawaban();
	}
	
	private void initSceneSkorTertinggi(){
		sceneSkorTertinggi.setBackground(spriteBackgroundSkorTertinggi);
		
		spriteTextSkorTertinggi = new Sprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, regionTextSkorTertinggi, vbo);
		animateSpriteGinko = new AnimatedSprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, regionAnimateGinko, vbo);
		animateSpriteGinko.animate(300); //ngatur kecepatan animasi
		
		sceneSkorTertinggi.attachChild(animateSpriteGinko);
		sceneSkorTertinggi.attachChild(spriteTextSkorTertinggi);
		sceneSkorTertinggi.attachChild(spriteBtnKembaliSkorTertinggi);
	}
	
	private void menuStart(){
		sceneMenu.setBackground(spriteBackgroundMenu);
		animateSpriteGinko = new AnimatedSprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, regionAnimateGinko, vbo);
		animateSpriteGinko.animate(300);
		
		groupMenuUtama = new Entity(0, 0);
		
		groupMenuUtama.attachChild(animateSpriteGinko);
		groupMenuUtama.attachChild(spriteBtnMulai);
		groupMenuUtama.attachChild(spriteBtnBantuan);
		groupMenuUtama.attachChild(spriteBtnSkor);
		groupMenuUtama.attachChild(spriteBtnKeluar);
		
		sceneMenu.attachChild(groupMenuUtama);
	}
	
	private void initDialogBox(){
		spriteDbQues = new Sprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, regionDbQues, vbo);
		spriteDbBack = new Sprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, regionDbBack, vbo);
		
		groupDialogBox = new Entity(0, 0);
		
		groupDialogBox.attachChild(spriteDbBack);
		groupDialogBox.attachChild(spriteDbQues);
		groupDialogBox.attachChild(spriteDbYes);
		groupDialogBox.attachChild(spriteDbNo);
		
		sceneDialogBox.attachChild(groupDialogBox);
	}
	
	private void menuBantuan(){
		sceneBantuan.setBackground(spriteBackgroundBantuan);
		sceneBantuan.attachChild(spriteBtnKembali);
	}
	
	private void initMenuSkor(){
		textSaveNamaNilai1 = new Text(CAMERA_WIDTH/2, (CAMERA_HEIGHT/2)+80, fontDaftarSkor, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", vbo);
		textSaveNamaNilai2 = new Text(CAMERA_WIDTH/2, (CAMERA_HEIGHT/2)+20, fontDaftarSkor, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", vbo);
		textSaveNamaNilai3 = new Text(CAMERA_WIDTH/2, (CAMERA_HEIGHT/2)-40, fontDaftarSkor, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", vbo);
		
		sceneDaftarSkor.setBackground(spriteBackgroundDaftarSkor);
		sceneDaftarSkor.attachChild(textSaveNamaNilai1);
		sceneDaftarSkor.attachChild(textSaveNamaNilai2);
		sceneDaftarSkor.attachChild(textSaveNamaNilai3);
		sceneDaftarSkor.attachChild(spriteBtnKembaliSkor);
	}
	
	private void initInputNama(){
		sceneInputNama.setBackground(spriteBackgroundInputNama);
		textInputNama = new Text(CAMERA_WIDTH/2, (CAMERA_HEIGHT/2)+100, fontAvg, "hahahahahahahahahahhaahahahahahahaha", vbo);
		textInputNama.setText("");
		
		groupSceneKeyboard = new Entity(0, 0);
		
		groupSceneKeyboard.attachChild(spriteBtnSaveNama);
		groupSceneKeyboard.attachChild(textInputNama);
		groupSceneKeyboard.attachChild(spriteBtnClr);
		
		for(int i=0; i<26; i++){
			spriteKeyboard[i].setAlpha(0.1f);
			groupSceneKeyboard.attachChild(spriteKeyboard[i]);
		}
		
		sceneInputNama.attachChild(groupSceneKeyboard);
	}
	
	private void alertKalah(){
		textNilaiSkor = new Text(CAMERA_WIDTH/2, (CAMERA_HEIGHT/2)-50, fontNilaiSkor, "hahahahahahahahahahahahaha", vbo);
		textNamaSkor = new Text(CAMERA_WIDTH/2, (CAMERA_HEIGHT/2)+130, fontKesempatan, "hahahahahahahahahahahahahahahahaha", vbo);
		textNamaSkor.setText("PAJAR");
		textNilaiSkor.setText("30");
		sceneKalah.attachChild(spriteKalah);
		sceneKalah.attachChild(textNilaiSkor);
		//sceneKalah.attachChild(textNamaSkor);
	}
	
	private void menuMain(){
		sceneMain.setBackground(spriteBackgroundMain);
		spriteBgSoal = new Sprite(CAMERA_WIDTH/2, 470, regionBgSoal, vbo);
		spriteGambarDiSoal = new Sprite(CAMERA_WIDTH/2, 430, regionGambarDiSoal, vbo);
		
		textSoal = new Text(CAMERA_WIDTH/2, 600, fontAvg, daftarSoal[0]+"hahahahahahahahaha", vbo);
		textKesempatan = new Text(35, 760, fontKesempatan, "hahahahahaha", vbo);
		
		textJawabA = new Text(CAMERA_WIDTH/4, 220, fontAvg, "Ini adalah pilihan A", vbo);
		textJawabB = new Text(CAMERA_WIDTH/4, 100, fontAvg, "Ini adalah pilihan B", vbo);
		textJawabC = new Text(CAMERA_WIDTH-(CAMERA_WIDTH/4), 220, fontAvg, "Ini adalah pilihan C", vbo);
		textJawabD = new Text(CAMERA_WIDTH-(CAMERA_WIDTH/4), 100, fontAvg, "Ini adalah pilihan D", vbo);
		
		spriteTrueMark = new Sprite(-60, -60, regionTrueMark, vbo);
		spriteFalseMarkA = new Sprite(-60, -60, regionFalseMark, vbo);
		spriteFalseMarkB = new Sprite(-60, -60, regionFalseMark, vbo);
		spriteFalseMarkC = new Sprite(-60, -60, regionFalseMark, vbo);
		spriteFalseMarkD = new Sprite(-60, -60, regionFalseMark, vbo);
		
		fungsiSoal();
		fungsiGambarDiSoal();
		fungsiSoalSelanjutnya();
		
		groupMenuMain = new Entity(0, 0);
		
		groupMenuMain.attachChild(spriteBgSoal);
		groupMenuMain.attachChild(spriteBtnJwbA);
		groupMenuMain.attachChild(spriteBtnJwbB);
		groupMenuMain.attachChild(spriteBtnJwbC);
		groupMenuMain.attachChild(spriteBtnJwbD);
		groupMenuMain.attachChild(spriteBtnKembaliHome);
		groupMenuMain.attachChild(spriteGambarDiSoal);
		
		groupMenuMain.attachChild(spriteTrueMark);
		groupMenuMain.attachChild(spriteFalseMarkA);
		groupMenuMain.attachChild(spriteFalseMarkB);
		groupMenuMain.attachChild(spriteFalseMarkC);
		groupMenuMain.attachChild(spriteFalseMarkD);
		
		groupMenuMain.attachChild(textSoal);
		groupMenuMain.attachChild(textKesempatan);
		groupMenuMain.attachChild(textJawabA);
		groupMenuMain.attachChild(textJawabB);
		groupMenuMain.attachChild(textJawabC);
		groupMenuMain.attachChild(textJawabD);
		
		sceneMain.attachChild(groupMenuMain);	
	}
	
	private void menuSoal(){
		sceneSoal.attachChild(spriteSoal);
	}
	
	private void fungsiSoal(){
		textKesempatan.setText(kesempatan+"x");
		switch(hasilLcm){
			case 0:
				isSoal = daftarSoal[hasilLcm];
				isJawabanA = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(isJawabanA);
				textJawabB.setText(daftarJawaban[5]);
				textJawabC.setText(daftarJawaban[3]);
				textJawabD.setText(daftarJawaban[8]);
				break;
			case 1:
				isSoal = daftarSoal[hasilLcm];
				isJawabanB = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[9]);
				textJawabB.setText(isJawabanB);
				textJawabC.setText(daftarJawaban[5]);
				textJawabD.setText(daftarJawaban[7]);
				break;
			case 2:
				isSoal = daftarSoal[hasilLcm];
				isJawabanC = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[0]);
				textJawabB.setText(daftarJawaban[1]);
				textJawabC.setText(isJawabanC);
				textJawabD.setText(daftarJawaban[5]);
				break;
			case 3:
				isSoal = daftarSoal[hasilLcm];
				isJawabanD = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[7]);
				textJawabB.setText(daftarJawaban[4]);
				textJawabC.setText(daftarJawaban[6]);
				textJawabD.setText(isJawabanD);
				break;
			case 4:
				isSoal = daftarSoal[hasilLcm];
				isJawabanA = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(isJawabanA);
				textJawabB.setText(daftarJawaban[1]);
				textJawabC.setText(daftarJawaban[2]);
				textJawabD.setText(daftarJawaban[3]);
				break;
			case 5:
				isSoal = daftarSoal[hasilLcm];
				isJawabanB = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[8]);
				textJawabB.setText(isJawabanB);
				textJawabC.setText(daftarJawaban[0]);
				textJawabD.setText(daftarJawaban[2]);
				break;
			case 6:
				isSoal = daftarSoal[hasilLcm];
				isJawabanC = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[2]);
				textJawabB.setText(daftarJawaban[8]);
				textJawabC.setText(isJawabanC);
				textJawabD.setText(daftarJawaban[0]);
				break;
			case 7:
				isSoal = daftarSoal[hasilLcm];
				isJawabanD = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[4]);
				textJawabB.setText(daftarJawaban[5]);
				textJawabC.setText(daftarJawaban[6]);
				textJawabD.setText(isJawabanD);
				break;
			case 8:
				isSoal = daftarSoal[hasilLcm];
				isJawabanA = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(isJawabanA);
				textJawabB.setText(daftarJawaban[3]);
				textJawabC.setText(daftarJawaban[0]);
				textJawabD.setText(daftarJawaban[1]);
				break;
			case 9:
				isSoal = daftarSoal[hasilLcm];
				isJawabanB = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[5]);
				textJawabB.setText(isJawabanB);
				textJawabC.setText(daftarJawaban[4]);
				textJawabD.setText(daftarJawaban[7]);
				break;
			case 10:
				isSoal = daftarSoal[hasilLcm];
				isJawabanC = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[14]);
				textJawabB.setText(daftarJawaban[11]);
				textJawabC.setText(isJawabanC);
				textJawabD.setText(daftarJawaban[15]);
				break;
			case 11:
				isSoal = daftarSoal[hasilLcm];
				isJawabanD = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[17]);
				textJawabB.setText(daftarJawaban[14]);
				textJawabC.setText(daftarJawaban[16]);
				textJawabD.setText(isJawabanD);
				break;
			case 12:
				isSoal = daftarSoal[hasilLcm];
				isJawabanA = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(isJawabanA);
				textJawabB.setText(daftarJawaban[11]);
				textJawabC.setText(daftarJawaban[16]);
				textJawabD.setText(daftarJawaban[13]);
				break;
			case 13:
				isSoal = daftarSoal[hasilLcm];
				isJawabanB = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[18]);
				textJawabB.setText(isJawabanB);
				textJawabC.setText(daftarJawaban[10]);
				textJawabD.setText(daftarJawaban[12]);
				break;
			case 14:
				isSoal = daftarSoal[hasilLcm];
				isJawabanC = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[12]);
				textJawabB.setText(daftarJawaban[18]);
				textJawabC.setText(isJawabanC);
				textJawabD.setText(daftarJawaban[10]);
				break;
			case 15:
				isSoal = daftarSoal[hasilLcm];
				isJawabanD = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[14]);
				textJawabB.setText(daftarJawaban[12]);
				textJawabC.setText(daftarJawaban[16]);
				textJawabD.setText(isJawabanD);
				break;
			case 16:
				isSoal = daftarSoal[hasilLcm];
				isJawabanA = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(isJawabanA);
				textJawabB.setText(daftarJawaban[13]);
				textJawabC.setText(daftarJawaban[10]);
				textJawabD.setText(daftarJawaban[11]);
				break;
			case 17:
				isSoal = daftarSoal[hasilLcm];
				isJawabanB = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[15]);
				textJawabB.setText(isJawabanB);
				textJawabC.setText(daftarJawaban[14]);
				textJawabD.setText(daftarJawaban[19]);
				break;
			case 18:
				isSoal = daftarSoal[hasilLcm];
				isJawabanC = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[12]);
				textJawabB.setText(daftarJawaban[11]);
				textJawabC.setText(isJawabanC);
				textJawabD.setText(daftarJawaban[10]);
				break;
			case 19:
				isSoal = daftarSoal[hasilLcm];
				isJawabanD = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[14]);
				textJawabB.setText(daftarJawaban[15]);
				textJawabC.setText(daftarJawaban[16]);
				textJawabD.setText(isJawabanD);
				break;
			case 20:
				isSoal = daftarSoal[hasilLcm];
				isJawabanA = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(isJawabanA);
				textJawabB.setText(daftarJawaban[26]);
				textJawabC.setText(daftarJawaban[13]);
				textJawabD.setText(daftarJawaban[18]);
				break;
			case 21:
				isSoal = daftarSoal[hasilLcm];
				isJawabanB = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[19]);
				textJawabB.setText(isJawabanB);
				textJawabC.setText(daftarJawaban[25]);
				textJawabD.setText(daftarJawaban[27]);
				break;
			case 22:
				isSoal = daftarSoal[hasilLcm];
				isJawabanC = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[20]);
				textJawabB.setText(daftarJawaban[21]);
				textJawabC.setText(isJawabanC);
				textJawabD.setText(daftarJawaban[25]);
				break;
			case 23:
				isSoal = daftarSoal[hasilLcm];
				isJawabanD = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[27]);
				textJawabB.setText(daftarJawaban[24]);
				textJawabC.setText(daftarJawaban[26]);
				textJawabD.setText(isJawabanD);
				break;
			case 24:
				isSoal = daftarSoal[hasilLcm];
				isJawabanA = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(isJawabanA);
				textJawabB.setText(daftarJawaban[21]);
				textJawabC.setText(daftarJawaban[22]);
				textJawabD.setText(daftarJawaban[23]);
				break;
			case 25:
				isSoal = daftarSoal[hasilLcm];
				isJawabanB = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[28]);
				textJawabB.setText(isJawabanB);
				textJawabC.setText(daftarJawaban[20]);
				textJawabD.setText(daftarJawaban[22]);
				break;
			case 26:
				isSoal = daftarSoal[hasilLcm];
				isJawabanC = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[32]);
				textJawabB.setText(daftarJawaban[28]);
				textJawabC.setText(isJawabanC);
				textJawabD.setText(daftarJawaban[30]);
				break;
			case 27:
				isSoal = daftarSoal[hasilLcm];
				isJawabanD = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[34]);
				textJawabB.setText(daftarJawaban[35]);
				textJawabC.setText(daftarJawaban[36]);
				textJawabD.setText(isJawabanD);
				break;
			case 28:
				isSoal = daftarSoal[hasilLcm];
				isJawabanA = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(isJawabanA);
				textJawabB.setText(daftarJawaban[23]);
				textJawabC.setText(daftarJawaban[30]);
				textJawabD.setText(daftarJawaban[31]);
				break;
			case 29:
				isSoal = daftarSoal[hasilLcm];
				isJawabanB = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[33]);
				textJawabB.setText(isJawabanB);
				textJawabC.setText(daftarJawaban[24]);
				textJawabD.setText(daftarJawaban[37]);
				break;
			case 30:
				isSoal = daftarSoal[hasilLcm];
				isJawabanC = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[14]);
				textJawabB.setText(daftarJawaban[11]);
				textJawabC.setText(isJawabanC);
				textJawabD.setText(daftarJawaban[35]);
				break;
			case 31:
				isSoal = daftarSoal[hasilLcm];
				isJawabanD = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[27]);
				textJawabB.setText(daftarJawaban[14]);
				textJawabC.setText(daftarJawaban[36]);
				textJawabD.setText(isJawabanD);
				break;
			case 32:
				isSoal = daftarSoal[hasilLcm];
				isJawabanA = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(isJawabanA);
				textJawabB.setText(daftarJawaban[31]);
				textJawabC.setText(daftarJawaban[26]);
				textJawabD.setText(daftarJawaban[13]);
				break;
			case 33:
				isSoal = daftarSoal[hasilLcm];
				isJawabanB = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[38]);
				textJawabB.setText(isJawabanB);
				textJawabC.setText(daftarJawaban[10]);
				textJawabD.setText(daftarJawaban[22]);
				break;
			case 34:
				isSoal = daftarSoal[hasilLcm];
				isJawabanC = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[32]);
				textJawabB.setText(daftarJawaban[28]);
				textJawabC.setText(isJawabanC);
				textJawabD.setText(daftarJawaban[30]);
				break;
			case 35:
				isSoal = daftarSoal[hasilLcm];
				isJawabanD = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[24]);
				textJawabB.setText(daftarJawaban[22]);
				textJawabC.setText(daftarJawaban[36]);
				textJawabD.setText(isJawabanD);
				break;
			case 36:
				isSoal = daftarSoal[hasilLcm];
				isJawabanA = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(isJawabanA);
				textJawabB.setText(daftarJawaban[13]);
				textJawabC.setText(daftarJawaban[30]);
				textJawabD.setText(daftarJawaban[21]);
				break;
			case 37:
				isSoal = daftarSoal[hasilLcm];
				isJawabanB = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[25]);
				textJawabB.setText(isJawabanB);
				textJawabC.setText(daftarJawaban[34]);
				textJawabD.setText(daftarJawaban[39]);
				break;
			case 38:
				isSoal = daftarSoal[hasilLcm];
				isJawabanC = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[12]);
				textJawabB.setText(daftarJawaban[31]);
				textJawabC.setText(isJawabanC);
				textJawabD.setText(daftarJawaban[20]);
				break;
			case 39:
				isSoal = daftarSoal[hasilLcm];
				isJawabanD = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[34]);
				textJawabB.setText(daftarJawaban[35]);
				textJawabC.setText(daftarJawaban[36]);
				textJawabD.setText(isJawabanD);
				break;
			case 40:
				isSoal = daftarSoal[hasilLcm];
				isJawabanA = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(isJawabanA);
				textJawabB.setText(daftarJawaban[43]);
				textJawabC.setText(daftarJawaban[44]);
				textJawabD.setText(daftarJawaban[47]);
				break;
			case 41:
				isSoal = daftarSoal[hasilLcm];
				isJawabanB = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[32]);
				textJawabB.setText(isJawabanB);
				textJawabC.setText(daftarJawaban[43]);
				textJawabD.setText(daftarJawaban[40]);
				break;
			case 42:
				isSoal = daftarSoal[hasilLcm];
				isJawabanC = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[48]);
				textJawabB.setText(daftarJawaban[41]);
				textJawabC.setText(isJawabanC);
				textJawabD.setText(daftarJawaban[35]);
				break;
			case 43:
				isSoal = daftarSoal[hasilLcm];
				isJawabanD = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[47]);
				textJawabB.setText(daftarJawaban[24]);
				textJawabC.setText(daftarJawaban[36]);
				textJawabD.setText(isJawabanD);
				break;
			case 44:
				isSoal = daftarSoal[hasilLcm];
				isJawabanA = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(isJawabanA);
				textJawabB.setText(daftarJawaban[11]);
				textJawabC.setText(daftarJawaban[22]);
				textJawabD.setText(daftarJawaban[33]);
				break;
			case 45:
				isSoal = daftarSoal[hasilLcm];
				isJawabanB = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[28]);
				textJawabB.setText(isJawabanB);
				textJawabC.setText(daftarJawaban[40]);
				textJawabD.setText(daftarJawaban[22]);
				break;
			case 46:
				isSoal = daftarSoal[hasilLcm];
				isJawabanC = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[42]);
				textJawabB.setText(daftarJawaban[48]);
				textJawabC.setText(isJawabanC);
				textJawabD.setText(daftarJawaban[20]);
				break;
			case 47:
				isSoal = daftarSoal[hasilLcm];
				isJawabanD = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[24]);
				textJawabB.setText(daftarJawaban[15]);
				textJawabC.setText(daftarJawaban[6]);
				textJawabD.setText(isJawabanD);
				break;
			case 48:
				isSoal = daftarSoal[hasilLcm];
				isJawabanA = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(isJawabanA);
				textJawabB.setText(daftarJawaban[43]);
				textJawabC.setText(daftarJawaban[20]);
				textJawabD.setText(daftarJawaban[1]);
				break;
			case 49:
				isSoal = daftarSoal[hasilLcm];
				isJawabanB = daftarJawaban[hasilLcm];
				textSoal.setText(isSoal);
				textJawabA.setText(daftarJawaban[5]);
				textJawabB.setText(isJawabanB);
				textJawabC.setText(daftarJawaban[44]);
				textJawabD.setText(daftarJawaban[37]);
				break;
		}
	}
	
	private void fungsiGambarDiSoal(){
		textureSoal.clearTextureAtlasSources();
		BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureGambarDiSoal, getAssets(), "gfx/gambarDiSoal/soal" + (hasilLcm+1) + ".png", 0, 0);
	}
	
	private void fungsiSoalSelanjutnya(){
		getEngine().setScene(sceneMain);
		textureSoal.clearTextureAtlasSources();
		BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureSoal, getAssets(), "gfx/Soal/Soal" + (hasilLcm+1) + ".png", 0, 0);
	}
	
	private void fungsiSalah(){
		kesempatan--;
		textKesempatan.setText(kesempatan+"x");
		if(kesempatan < 0){
			kesempatan = 3;
			textNilaiSkor.setText(""+nilaiSkor);
			textNamaSkor.setText(saveNama);
			int spSaveNilai1 = sp.getInt("skor1", 0);
			int spSaveNilai2 = sp.getInt("skor2", 0);
			int spSaveNilai3 = sp.getInt("skor3", 0);
			if(nilaiSkor > spSaveNilai1){
				int tmpSkor1 = sp.getInt("skor1", 0);
				String tmpNama1 = sp.getString("nama1", "");
				int tmpSkor2 = sp.getInt("skor2", 0);
				String tmpNama2 = sp.getString("nama2", "");
				
				ed.putInt("skor2", tmpSkor1);
				ed.putString("nama2", tmpNama1);
				ed.putInt("skor3", tmpSkor2);
				ed.putString("nama3", tmpNama2);
				
				ed.putInt("skor1", nilaiSkor);
				ed.putString("nama1", saveNama);
				ed.commit();
			} else if(nilaiSkor > spSaveNilai2){
				int tmpSkor2 = sp.getInt("skor2", 0);
				String tmpNama2 = sp.getString("nama2", "");
				
				ed.putInt("skor3", tmpSkor2);
				ed.putString("nama3", tmpNama2);
				
				ed.putInt("skor2", nilaiSkor);
				ed.putString("nama2", saveNama);
				ed.commit();
			} else if(nilaiSkor > spSaveNilai3){
				ed.putInt("skor3", nilaiSkor);
				ed.putString("nama3", saveNama);
				ed.commit();
			}
			getEngine().setScene(sceneKalah);
			soundKalah.play();
		}
	}
	
	private void touchEvent(){
		//MENU UTAMA
		spriteBtnMulai = new Sprite(CAMERA_WIDTH/2, 400, regionBtnMulai, vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					hasilLcm = (int) (Math.random()*COUNT_SOAL);
					kesempatan = 3;
					textInputNama.setText("");
					saveNama = "";
					inputNama = "";
					nilaiSkor = 0;
					
					spriteFalseMarkA.setPosition(-60, -60);
					spriteFalseMarkB.setPosition(-60, -60);
					spriteFalseMarkC.setPosition(-60, -60);
					spriteFalseMarkD.setPosition(-60, -60);
					
					fungsiSoal();
					fungsiGambarDiSoal();
					fungsiSoalSelanjutnya();
					getEngine().setScene(sceneInputNama);
				}
				return true;
			}
		};
		sceneMenu.registerTouchArea(spriteBtnMulai);
		
		spriteBtnBantuan = new Sprite(CAMERA_WIDTH/2, 310, regionBtnBantuan, vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					getEngine().setScene(sceneBantuan);
				}
				return true;
			}
		};
		sceneMenu.registerTouchArea(spriteBtnBantuan);
		
		spriteBtnSkor = new Sprite(CAMERA_WIDTH/2, 220, regionBtnSkor, vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					
					String spSaveNama1 = sp.getString("nama1", "");
					int spSaveSkor1 = sp.getInt("skor1", 0);
					textSaveNamaNilai1.setText("1. "+spSaveNama1+" : "+spSaveSkor1);
					
					String spSaveNama2 = sp.getString("nama2", "");
					int spSaveSkor2 = sp.getInt("skor2", 0);
					textSaveNamaNilai2.setText("2. "+spSaveNama2+" : "+spSaveSkor2);
					
					String spSaveNama3 = sp.getString("nama3", "");
					int spSaveSkor3 = sp.getInt("skor3", 0);
					textSaveNamaNilai3.setText("3. "+spSaveNama3+" : "+spSaveSkor3);
					
					getEngine().setScene(sceneDaftarSkor);
				}
				return true;
			}
		};
		sceneMenu.registerTouchArea(spriteBtnSkor);
		
		spriteBtnKeluar = new Sprite(CAMERA_WIDTH/2, 130, regionBtnKeluar, vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					getEngine().setScene(sceneDialogBox);
				}
				return true;
			}
		};
		sceneMenu.registerTouchArea(spriteBtnKeluar);
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//DIALOG BOX
		spriteDbYes = new Sprite((CAMERA_WIDTH/2)-10, (CAMERA_HEIGHT/2)-140, regionDbYes, vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					System.exit(0);
				}
				return true;
			}
		};
		sceneDialogBox.registerTouchArea(spriteDbYes);
		
		spriteDbNo = new Sprite((CAMERA_WIDTH/2)+150, (CAMERA_HEIGHT/2)-80, regionDbNo, vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					getEngine().setScene(sceneMenu);
				}
				return true;
			}
		};
		sceneDialogBox.registerTouchArea(spriteDbNo);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//MENU BANTUAN
		spriteBtnKembali = new Sprite(CAMERA_WIDTH/2, 160, regionBtnKembali, vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					getEngine().setScene(sceneMenu);
				}
				return true;
			}
		};
		sceneBantuan.registerTouchArea(spriteBtnKembali);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//MENU DAFTAR SKOR
		spriteBtnKembaliSkor = new Sprite(CAMERA_WIDTH/2, 160, regionBtnKembali, vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					getEngine().setScene(sceneMenu);
				}
				return true;
			}
		};
		sceneDaftarSkor.registerTouchArea(spriteBtnKembaliSkor);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//MENU MAIN
		spriteBtnJwbA = new Sprite(CAMERA_WIDTH/4, 220, regionBtnJwbA, vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					boolean isTrue = daftarSoalDanJawaban.cekJawaban(isSoal, isJawabanA);
					if(isTrue){
						nilaiSkor++;
						getEngine().setScene(sceneSoal);
						soundSoalBenar[hasilLcm].play();
					}
					else{
						fungsiSalah();
						spriteFalseMarkA.setPosition((CAMERA_WIDTH/4)-97, 277);
					}
				}
				return true;
			}
		};
		sceneMain.registerTouchArea(spriteBtnJwbA);
		
		spriteBtnJwbB = new Sprite(CAMERA_WIDTH/4, 100, regionBtnJwbB, vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					boolean isTrue = daftarSoalDanJawaban.cekJawaban(isSoal, isJawabanB);
					if(isTrue){
						nilaiSkor++;
						getEngine().setScene(sceneSoal);
						soundSoalBenar[hasilLcm].play();
					}
					else{
						fungsiSalah();
						spriteFalseMarkB.setPosition((CAMERA_WIDTH/4)-97, 43);
					}
				}
				return true;
			}
		};
		sceneMain.registerTouchArea(spriteBtnJwbB);
		
		spriteBtnJwbC = new Sprite(CAMERA_WIDTH-(CAMERA_WIDTH/4), 220, regionBtnJwbC, vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					boolean isTrue = daftarSoalDanJawaban.cekJawaban(isSoal, isJawabanC);
					if(isTrue){
						nilaiSkor++;
						getEngine().setScene(sceneSoal);
						soundSoalBenar[hasilLcm].play();
					}
					else{
						fungsiSalah();
						spriteFalseMarkC.setPosition((CAMERA_WIDTH-(CAMERA_WIDTH/4))-97, 277);
					}
				}
				return true;
			}
		};
		sceneMain.registerTouchArea(spriteBtnJwbC);
		
		spriteBtnJwbD = new Sprite(CAMERA_WIDTH-(CAMERA_WIDTH/4), 100, regionBtnJwbD, vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					boolean isTrue = daftarSoalDanJawaban.cekJawaban(isSoal, isJawabanD);
					if(isTrue){
						nilaiSkor++;
						getEngine().setScene(sceneSoal);
						soundSoalBenar[hasilLcm].play();
					}
					else{
						fungsiSalah();
						spriteFalseMarkD.setPosition((CAMERA_WIDTH-(CAMERA_WIDTH/4))-97, 43);
					}
				}
				return true;
			}
		};
		sceneMain.registerTouchArea(spriteBtnJwbD);
		
		spriteBtnKembaliHome = new Sprite(430, 750, regionBtnKembaliHome, vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					musicBacksound.resume();
					getEngine().setScene(sceneMenu);
				}
				return true;
			}
		};
		sceneMain.registerTouchArea(spriteBtnKembaliHome);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//SOAL SELANJUTNYA
		spriteSoal = new Sprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, regionSoal, vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionUp()){
					if(nilaiSkor >= SKOR_MAX){
						soundMenang.play();
						int tmpSkor1 = sp.getInt("skor1", 0);
						String tmpNama1 = sp.getString("nama1", "");
						int tmpSkor2 = sp.getInt("skor2", 0);
						String tmpNama2 = sp.getString("nama2", "");
						
						ed.putInt("skor2", tmpSkor1);
						ed.putString("nama2", tmpNama1);
						ed.putInt("skor3", tmpSkor2);
						ed.putString("nama3", tmpNama2);
						
						ed.putInt("skor1", nilaiSkor);
						ed.putString("nama1", saveNama);
						ed.commit();
						getEngine().setScene(sceneSkorTertinggi);
					}else{
						hasilLcm = ((konsA * x0) + konsC) % limitMax; //METODE LCM
						x0 = hasilLcm;
						fungsiSoal();
						fungsiGambarDiSoal();
						fungsiSoalSelanjutnya();
						
						spriteFalseMarkA.setPosition(-60, -60);
						spriteFalseMarkB.setPosition(-60, -60);
						spriteFalseMarkC.setPosition(-60, -60);
						spriteFalseMarkD.setPosition(-60, -60);
						
						getEngine().setScene(sceneMain);
					}
				}
				return true;
			}
		};
		sceneSoal.registerTouchArea(spriteSoal);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//KALAH
		spriteKalah = new Sprite(CAMERA_WIDTH/2, CAMERA_HEIGHT/2, regionKalah, vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionUp()){
					musicBacksound.resume();
					getEngine().setScene(sceneMenu);
				}
				return true;
			}
		};
		sceneKalah.registerTouchArea(spriteKalah);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//SKOR TERTINGGI
		spriteBtnKembaliSkorTertinggi = new Sprite(CAMERA_WIDTH/2, 160, regionBtnKembali, vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					musicBacksound.resume();
					getEngine().setScene(sceneMenu);
				}
				return true;
			}
		};
		sceneSkorTertinggi.registerTouchArea(spriteBtnKembaliSkorTertinggi);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		deklarasiKeyboard();
		
		sceneDaftarSkor.setTouchAreaBindingOnActionDownEnabled(true);
		sceneDialogBox.setTouchAreaBindingOnActionDownEnabled(true);
		sceneSkorTertinggi.setTouchAreaBindingOnActionDownEnabled(true);
		sceneMenu.setTouchAreaBindingOnActionDownEnabled(true);
		sceneSoal.setTouchAreaBindingOnActionDownEnabled(true);
		sceneBantuan.setTouchAreaBindingOnActionDownEnabled(true);
		sceneKalah.setTouchAreaBindingOnActionDownEnabled(true);
		sceneMain.setTouchAreaBindingOnActionDownEnabled(true);
	}
	
	private void deklarasiKeyboard(){
		spriteBtnSaveNama = new Sprite(CAMERA_WIDTH/2, (CAMERA_HEIGHT/2), regionBtnSaveNama, vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					saveNama = inputNama;
					musicBacksound.pause();
					getEngine().setScene(sceneMain);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteBtnSaveNama);
		
		//DEKLARASI TOP KEYBOARD
		spriteKeyboard[16] = new Sprite(34, TOP_KEYOARD, regionKeyboard[16], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "Q";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[16]);
		
		spriteKeyboard[22] = new Sprite(80, TOP_KEYOARD, regionKeyboard[22], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "W";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[22]);
		
		spriteKeyboard[4] = new Sprite(126, TOP_KEYOARD, regionKeyboard[4], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "E";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[4]);
		
		spriteKeyboard[17] = new Sprite(172, TOP_KEYOARD, regionKeyboard[17], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "R";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[17]);
		
		spriteKeyboard[19] = new Sprite(218, TOP_KEYOARD, regionKeyboard[19], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "T";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[19]);
		
		spriteKeyboard[24] = new Sprite(264, TOP_KEYOARD, regionKeyboard[24], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "Y";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[24]);
		
		spriteKeyboard[20] = new Sprite(310, TOP_KEYOARD, regionKeyboard[20], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "U";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[20]);
		
		spriteKeyboard[8] = new Sprite(356, TOP_KEYOARD, regionKeyboard[8], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "I";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[8]);
		
		spriteKeyboard[14] = new Sprite(402, TOP_KEYOARD, regionKeyboard[14], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "O";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[14]);
		//438
		spriteKeyboard[15] = new Sprite(448, TOP_KEYOARD, regionKeyboard[15], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "P";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[15]);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//DEKLARASI MID KEYBOARD
		spriteKeyboard[0] = new Sprite(57, MID_KEYOARD, regionKeyboard[0], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "A";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[0]);
		
		spriteKeyboard[18] = new Sprite(103, MID_KEYOARD, regionKeyboard[18], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "S";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[18]);
		
		spriteKeyboard[3] = new Sprite(149, MID_KEYOARD, regionKeyboard[3], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "D";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[3]);
		
		spriteKeyboard[5] = new Sprite(195, MID_KEYOARD, regionKeyboard[5], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "F";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[5]);
		
		spriteKeyboard[6] = new Sprite(241, MID_KEYOARD, regionKeyboard[6], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "G";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[6]);
		
		spriteKeyboard[7] = new Sprite(287, MID_KEYOARD, regionKeyboard[7], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "H";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[7]);
		
		spriteKeyboard[9] = new Sprite(333, MID_KEYOARD, regionKeyboard[9], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "J";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[9]);
		
		spriteKeyboard[10] = new Sprite(379, MID_KEYOARD, regionKeyboard[10], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "K";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[10]);
		
		spriteKeyboard[11] = new Sprite(425, MID_KEYOARD, regionKeyboard[11], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "L";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[11]);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//DEKLARASI BOTTOM KEYBOARD
		spriteKeyboard[25] = new Sprite(103, BOTTOM_KEYOARD, regionKeyboard[25], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "Z";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[25]);
		
		spriteKeyboard[23] = new Sprite(149, BOTTOM_KEYOARD, regionKeyboard[23], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "X";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[23]);
		
		spriteKeyboard[2] = new Sprite(195, BOTTOM_KEYOARD, regionKeyboard[2], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "C";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[2]);
		
		spriteKeyboard[21] = new Sprite(241, BOTTOM_KEYOARD, regionKeyboard[21], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "V";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[21]);
		
		spriteKeyboard[1] = new Sprite(287, BOTTOM_KEYOARD, regionKeyboard[1], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "B";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[1]);
		
		spriteKeyboard[13] = new Sprite(333, BOTTOM_KEYOARD, regionKeyboard[13], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "N";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[13]);
		
		spriteKeyboard[12] = new Sprite(379, BOTTOM_KEYOARD, regionKeyboard[12], vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama += "M";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteKeyboard[12]);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		spriteBtnClr = new Sprite(435, BOTTOM_KEYOARD, regionBtnClr, vbo){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionDown())
					this.setScale(0.7f);
				else if(pSceneTouchEvent.isActionUp()){
					this.setScale(1);
					inputNama = "";
					textInputNama.setText(inputNama);
				}
				return true;
			}
		};
		sceneInputNama.registerTouchArea(spriteBtnClr);
		
		sceneInputNama.setTouchAreaBindingOnActionDownEnabled(true);
	}
}
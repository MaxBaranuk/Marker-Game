package com.apr.markergame.screens;


import com.apr.markergame.ActionResolver;
import com.apr.markergame.MarkerGame;
import com.apr.markergame.game.Level;
import com.apr.markergame.game.PassedLevel;
import com.apr.markergame.game.gamelevel.GameLevel;
import com.apr.markergame.game.gamelevel.GameLevelListener;
import com.apr.markergame.services.MarkerSound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class LevelScreen extends AbstractGameScreen
implements GameLevelListener{

	MarkerGame game;
	Dialog instructionsDialog;
	Dialog passedStepDialog;
	Dialog passedLevelDialog;
	Dialog failedDialog;
	Dialog pauseDialog;
	OkDialog unlockPackDialog;
	OkDialog noMoreLevelsdialog;
	OkDialog cannotUnlockdialog,cannotUnlockdialog2;
	protected ActionResolver actionResolver;

	Label scoreLabel, stepLabel, averageLabel,numStarsLabel;
	GameLevel gameLevel;
//	Texture background;

	int numPasses;
	int maxPasses;
	int numStars;
	float avg;
	float[] scores;
	Array<Float> stars;
	Label currentPass;
	Table topTable;
	public LevelScreen(MarkerGame game1) {
		super(game1);
		game = game1;
		this.actionResolver = game.actionResolver;
	}
	
    @Override
    public void show()
    {
        super.show();
        numPasses = 0;
        

        


	    Level currentLevel = world.getLevel();
        maxPasses = currentLevel.numTimes;
        scores = new float[maxPasses];
	    stars = currentLevel.stars;
        super.getTable();
        
        
		try {
			@SuppressWarnings("rawtypes")
			Class glClass = Class.forName("com.apr.markergame.game.gamelevel." + currentLevel.gameLevel);
			gameLevel = (GameLevel)glClass.newInstance();
	        gameLevel.setGameLevelListener(this);
	        gameLevel.setAssets(game.assetManager);
	        gameLevel.setFactor(currentLevel.factor);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}catch (InstantiationException e) {
			e.printStackTrace();
		}
        
		
		

		
		Table leftTable = new Table();
		currentPass = new Label("Pass 1/" + currentLevel.numTimes, getSkin(),"title-level");
		leftTable.add(currentPass);

		Table rightTable = new Table();
		Label label = new Label("Level: " + currentLevel.name, getSkin (),"title-level");
		Button button = new Button(getSkin());
		button.getStyle().up = getSkin().getDrawable("level" + currentLevel.id);
		rightTable.add(label);
		rightTable.add(button).size(40,40);
		
		topTable = new Table();
		topTable.setBackground(getSkin().getDrawable("fog"));
		topTable.add(leftTable).expandX();
		topTable.add(rightTable).width(340);
		
		

		TextButton backButton = new TextButton(" ||  ",getSkin());
		backButton.addListener( new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
            	gameLevel.pause();
            	pauseDialog.show(getTable().getStage());
            	
            }
        } );
        topTable.add( backButton ).width(50) ;
      
        table.add(topTable).expandY().top().fillX().expandX();
	
        table.row();

        createInstructionsDialog(currentLevel);
        createFailedDialog();
        createPassStep();
        createPassLevel();
        createPauseDialog();
        
    	unlockPackDialog = new OkDialog("Unlocked pack", getSkin(),"Congratulations you unlocked this pack",new ClickListener(){
   		 public void clicked (InputEvent event, float x, float y)
         {
			 game.setScreen(new SelectLevelScreen(game));
         }
	});
    	noMoreLevelsdialog= new OkDialog("Coming soon", getSkin(),"Wait for our uptades for more great levels",new ClickListener(){
    		 public void clicked (InputEvent event, float x, float y)
             {
    			 game.setScreen(new SelectLevelScreen(game));
             }
    	});
    	cannotUnlockdialog= new OkDialog("Not enough stars", getSkin(),"You cannot unlock the next pack yet, you need more stars",new ClickListener(){
   		 public void clicked (InputEvent event, float x, float y)
         {
			 game.setScreen(new SelectLevelScreen(game));
         }
    	});
   		cannotUnlockdialog2= new OkDialog("Not enough stars", getSkin(),"You need at least 3 stars to unlock next level",new ClickListener(){
      		 public void clicked (InputEvent event, float x, float y)
            {
   			 game.setScreen(new SelectLevelScreen(game));
            }
     	});

	
    }
    
    private void createInstructionsDialog(Level level){
        TextButton okButton = new TextButton("Start",getSkin());
        okButton.getLabel().getStyle().fontColor = getSkin().getColor("red");
        okButton.addListener( new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
            	gameLevel.startGame();
            	instructionsDialog.hide();
            }
        } );
        Label instructionsLabel = new Label("Instructions",getSkin(), "title-text");
        instructionsLabel.getStyle().fontColor = Color.BLACK;
        Label textLabel = new Label(level.instructions,getSkin(), "text");
        textLabel.getStyle().fontColor = Color.BLACK;
        textLabel.setAlignment(Align.center);
        textLabel.setWrap(true);        
        instructionsDialog = new Dialog("",getSkin());
        instructionsDialog.defaults();
        instructionsDialog.columnDefaults(1);
        instructionsDialog.row();
        instructionsDialog.add(instructionsLabel).padTop(70);
        instructionsDialog.row();
        instructionsDialog.add(textLabel).width(350);
        instructionsDialog.row();
        instructionsDialog.add(okButton).padBottom(50);
        
        instructionsDialog.show(table.getStage());

    }

    private void createFailedDialog(){
        Label failedLabel = new Label("Failed",getSkin(), "title-text");
        TextButton okFailedButton = new TextButton("Restart",getSkin());
        okFailedButton.getLabel().getStyle().fontColor = getSkin().getColor("green");
        okFailedButton.addListener( new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
            	gameLevel.startGame();
            	failedDialog.hide();
            }
        } );
        
        TextButton backButton = new TextButton("Back to menu",getSkin());
        backButton.getLabel().getStyle().fontColor = getSkin().getColor("blue");
        backButton.addListener( new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
            	game.setScreen(new MenuScreen(game));
            }
        } );
        failedDialog = new Dialog("",getSkin());
        failedDialog.defaults();
        failedDialog.columnDefaults(1);
        failedDialog.row();
        failedDialog.add(failedLabel).padTop(20);
        failedDialog.row();
        failedDialog.add(okFailedButton);
        failedDialog.row();
        failedDialog.add(backButton).padBottom(30);

    }
    
    private void createPassStep(){
        Label congratLabel = new Label("Congratulations",getSkin(), "title-text");
        congratLabel.getStyle().fontColor = getSkin().getColor("blue");
        TextButton okFailedButton = new TextButton("Continue",getSkin());
        okFailedButton.getLabel().getStyle().fontColor = getSkin().getColor("green");
        okFailedButton.addListener( new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
            	gameLevel.startPass();
            	passedStepDialog.hide();
            }
        } );
        
        TextButton backButton = new TextButton("Back to menu",getSkin());
        backButton.getLabel().getStyle().fontColor = getSkin().getColor("blue");
        scoreLabel = new Label("Score: " + scores[numPasses] ,getSkin(), "text");
        scoreLabel.getStyle().fontColor = Color.BLACK;
        stepLabel = new Label("You passed sublevel : " + numPasses ,getSkin(), "text");
        stepLabel.getStyle().fontColor = Color.BLACK;

        backButton.addListener( new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
            	game.setScreen(new MenuScreen(game));
            }
        } );
        passedStepDialog = new Dialog("",getSkin());
        passedStepDialog.defaults();
        passedStepDialog.columnDefaults(1);
        passedStepDialog.row();
        passedStepDialog.add(congratLabel).padTop(20);
        passedStepDialog.row();
        passedStepDialog.add(stepLabel);
        passedStepDialog.row();
        passedStepDialog.add(scoreLabel);
        passedStepDialog.row();
        passedStepDialog.add(okFailedButton);
        passedStepDialog.row();
        passedStepDialog.add(backButton).padBottom(30);
        

    }

    
    private void createPassLevel(){
        Label failedLabel = new Label("Congratulations",getSkin(), "title-text");
        failedLabel.getStyle().fontColor = getSkin().getColor("blue");

        TextButton okFailedButton = new TextButton("Go to next level",getSkin());
        okFailedButton.getLabel().getStyle().fontColor = getSkin().getColor("green");
        okFailedButton.addListener( new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
            	PassedLevel passedLevelAnswer = world.getNextLevel();
            	game.getProfileManager().persist();
            	
            	switch (passedLevelAnswer){
            		case NEXT_LEVEL:
                    	runOverlay();
                    	if (world.getLevel()==null) return;
                    	game.setScreen(new LevelScreen(game));

            			break;
            		case NOT_ENOUGH_STARS:
            			cannotUnlockdialog.show(getTable().getStage());
            			break;
            		case NOT_3_STARS:
            			cannotUnlockdialog2.show(getTable().getStage());
            			break;
            		case NEXT_PACK:
            			unlockPackDialog.show(getTable().getStage());
            			break;
            		case NO_MORE_LEVELS:
            			noMoreLevelsdialog.show(getTable().getStage());
            			break;
            	}	
            	passedLevelDialog.hide();

            }
        } );
        
        averageLabel = new Label("Average score: ", getSkin(), "text");
        averageLabel.getStyle().fontColor = Color.BLACK;
        numStarsLabel = new Label("Num Stars: ", getSkin(), "text");
        numStarsLabel.getStyle().fontColor = Color.BLACK;
        
        TextButton restartButton = new TextButton("Restart",getSkin());
        restartButton.getLabel().getStyle().fontColor = getSkin().getColor("blue");
        restartButton.addListener( new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
            	
        		numPasses = 0;
            	currentPass.setText("Pass 1/" + maxPasses);
            	gameLevel.startGame();
            	passedLevelDialog.hide();
            }
        } );

        
        TextButton backButton = new TextButton("Back to menu",getSkin());
        backButton.getLabel().getStyle().fontColor = getSkin().getColor("blue");
        backButton.addListener( new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
            	game.setScreen(new MenuScreen(game));
            }
        } );
        passedLevelDialog = new Dialog("",getSkin());
        passedLevelDialog.defaults();
        passedLevelDialog.columnDefaults(1);
        passedLevelDialog.row();
        passedLevelDialog.add(failedLabel).padTop(20);
        passedLevelDialog.row();
        passedLevelDialog.add(averageLabel);
        passedLevelDialog.row();
        passedLevelDialog.add(numStarsLabel);
        passedLevelDialog.row();
        passedLevelDialog.add(okFailedButton);
        passedLevelDialog.row();
        passedLevelDialog.add(restartButton);
        passedLevelDialog.row();
        passedLevelDialog.add(backButton).padBottom(30);
        

    }
    
    private void createPauseDialog(){
        Label label = new Label("Pause",getSkin(), "title-text");
        label.getStyle().fontColor = getSkin().getColor("blue");

        TextButton resumeButton = new TextButton("Resume",getSkin());
        resumeButton.getLabel().getStyle().fontColor = getSkin().getColor("green");
        resumeButton.addListener( new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
            	gameLevel.resume();
            	pauseDialog.hide();
            }
        } );
        
        TextButton restartButton = new TextButton("Restart",getSkin());
        restartButton.getLabel().getStyle().fontColor = getSkin().getColor("green");
        restartButton.addListener( new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
            	gameLevel.startGame();
            	pauseDialog.hide();

            }
        } );
        
        TextButton menuButton = new TextButton("Back to menu",getSkin());
        menuButton.getLabel().getStyle().fontColor = getSkin().getColor("green");
        menuButton.addListener( new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
            	game.setScreen(new MenuScreen(game));
            }
        } );
        
        
        pauseDialog = new Dialog("",getSkin());
        pauseDialog.defaults();
        pauseDialog.columnDefaults(1);
        pauseDialog.row();
        pauseDialog.add(label).padTop(20);
        pauseDialog.row();
        pauseDialog.add(resumeButton);
        pauseDialog.row();
        pauseDialog.add(restartButton);
        pauseDialog.row();
        pauseDialog.add(menuButton).padBottom(30);
        

    }

    public void render(float delta){
    	super.render(delta);
     	gameLevel.update(delta);

        
     	if (gameLevel!=null){    
    		gameLevel.render();
    	}

         
//         renderer.render(delta);
         // update and draw the stage actors
         stage.draw();
      //   Table.drawDebug(stage);
    }

	@Override
	public void passStep(float score) {
		scores[numPasses] = score;
		scoreLabel.setText("Score: " + score);
		numPasses++;
		stepLabel.setText("You passed sublevel: " + numPasses);
		if (numPasses<maxPasses){
			game.getSoundManager().play(MarkerSound.PASS_STEP);
        	gameLevel.startPass();
        	currentPass.setColor(Color.GREEN);
        	currentPass.addAction(Actions.color(Color.WHITE, 1f));
        	currentPass.setText("Pass " + (numPasses+1) +"/" + maxPasses);
			//passedStepDialog.show(stage);
		}else{
			game.getSoundManager().play(MarkerSound.PASS_LEVEL);

			avg = 0;
			for (int i=0;i<maxPasses;i++) avg+=scores[i];
			avg = avg/(float)maxPasses;
			
			numStars = 0;
			for (int i=0;i<stars.size;i++){
				if (avg<=stars.get(i)){
					numStars = 5-i;
					break;
				}
			}
			averageLabel.setText("Average score: " + avg);
			numStarsLabel.setText("Num Stars: " + numStars);
        	
			world.passLevel(numStars, score);
			game.getProfileManager().persist();
			
			passedLevelDialog.show(stage);
			actionResolver.showOrLoadInterstital();
		}
	}

	@Override
	public void fail() {
		numPasses = 0;
		failedDialog.show(this.stage);
    	currentPass.setText("Pass 1/" + maxPasses);
    	game.getSoundManager().play(MarkerSound.FAIL);
	}
	

	@Override
	protected void backAction() {
    	gameLevel.pause();

		pauseDialog.show(getTable().getStage());
		
	}

	@Override
	protected void touch(float x, float y) {
		gameLevel.touch(x, y);
		
	}
	
	@Override
	protected void drag(float x, float y) {
		gameLevel.drag(x, y);
		
	}

	@Override
	public void playSound(MarkerSound sound) {
		game.getSoundManager().play(sound);
		
	}

	
	private void runOverlay(){
//		if (random.nextFloat()>0.3)
//			game.getAdListener().startOverlay();	
	}

}

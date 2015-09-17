
/**
 * Created by Tess Daughton, September 13th 2015
 */
package Resources;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.embed.swing.JFXPanel;
import java.util.concurrent.*;
import java.nio.file.Paths;
public class SoundLoader
{

  public SoundLoader()
  {
    this.playBackgroundMusic();
  }

  public void rightFootStep()
  {
    try
    {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(("src/Resources/sound_resources/fantasy_sound/Wav/Footsteps/Footstep_Dirt_02.wav")).getAbsoluteFile());
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.start();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public void leftFootStep()
  {
    try
    {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(("src/Resources/sound_resources/fantasy_sound/Wav/Footsteps/Footstep_Dirt_03.wav")).getAbsoluteFile());
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.start();

    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public void playBackgroundMusic()
  {

    try
    {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(("src/Resources/sound_resources/Haunted_Woods/Haunted_Woods_Loop.wav")).getAbsoluteFile());
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.start();

    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }
}
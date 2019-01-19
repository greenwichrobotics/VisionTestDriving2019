/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private DifferentialDrive m_drive;
  private Spark m_frontLeft;
  private Spark m_frontRight;
  private Spark m_rearLeft;
  private Spark m_rearRight;
  private SpeedControllerGroup m_left;
  private SpeedControllerGroup m_right;
  private Joystick m_joystick;
  private MqttConnection m_broker;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    m_frontLeft = new Spark(0);
    m_rearLeft = new Spark(1);
    m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);

    m_frontRight = new Spark(2);
    m_rearRight = new Spark(3);
    m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);

    m_drive = new DifferentialDrive(m_left, m_right);

    m_joystick = new Joystick(0);

    m_broker = new MqttConnection("tcp://10.64.84.109:1883");
    m_broker.subscribe("test");


  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // autoSelected = SmartDashboard.getString("Auto Selector",
    // defaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // switch (m_autoSelected) {
    //   case kCustomAuto:
    //     // Put custom auto code here
    //     break;
    //   case kDefaultAuto:
    //   default:
    //     // Put default auto code here
    //     break;
    // }

    MqttMessage lastOne = m_broker.getLastMessage();
    if(lastOne.toString() != "(0,0)"){
      System.out.println(lastOne);

    }
  m_drive.arcadeDrive(-0.5, 0.0);
  
  }


  @Override
  public void teleopInit(){
    // String topic        = "test";
    // String content      = "Hello From Taz";
    // int qos             = 2;
    // String broker       = "tcp://10.64.84.177:1883";
    // String clientId     = UUID.randomUUID().toString();
    // MemoryPersistence persistence = new MemoryPersistence();

    // try {
    //     MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
    //     MqttConnectOptions connOpts = new MqttConnectOptions();
    //     connOpts.setCleanSession(true);
    //     System.out.println("Connecting to broker: "+broker);
    //     sampleClient.connect(connOpts);
    //     System.out.println("Connected");
    //     System.out.println("Publishing message: "+content);
    //     MqttMessage message = new MqttMessage(content.getBytes());
    //     message.setQos(qos);
    //     sampleClient.publish(topic, message);
    //     System.out.println("Message published");
    //     sampleClient.disconnect();
    //     System.out.println("Disconnected");
    //     System.exit(0);
    // } catch(MqttException me) {
    //     System.out.println("reason "+me.getReasonCode());
    //     System.out.println("msg "+me.getMessage());
    //     System.out.println("loc "+me.getLocalizedMessage());
    //     System.out.println("cause "+me.getCause());
    //     System.out.println("excep "+me);
    //     me.printStackTrace();
    // }
  }
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  m_drive.arcadeDrive(m_joystick.getRawAxis(1), 0.0);

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}

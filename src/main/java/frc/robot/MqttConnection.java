/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.UUID;


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Add your docs here.
 */
public class MqttConnection implements MqttCallback {
    private String clientId;
    private MemoryPersistence persistence;
    private MqttClient client;
    private MqttConnectOptions connOpts;
    private MqttMessage lastMessage;

    public MqttConnection(String connection) {
        try {
            clientId = UUID.randomUUID().toString();
            persistence = new MemoryPersistence();
            client = new MqttClient(connection, clientId, persistence);
            connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            // System.out.println("Connecting to broker: "+broker);
            client.connect(connOpts);
            client.setCallback(this);
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }

    }

    public void subscribe(String topic){
        try{
            this.client.subscribe(topic);
        }catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        this.lastMessage = message;
        // System.out.println(message);

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    /**
     * @return the lastMessage
     */
    public MqttMessage getLastMessage() {
        return lastMessage;
    }

    // String topic = "test";
    // String content = "Hello From Taz";
    // int qos = 2;
    // String broker = "tcp://10.64.84.177:1883";
    // String clientId = UUID.randomUUID().toString();
    // MemoryPersistence persistence = new MemoryPersistence();

    // try {
    // MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
    // MqttConnectOptions connOpts = new MqttConnectOptions();
    // connOpts.setCleanSession(true);
    // System.out.println("Connecting to broker: "+broker);
    // sampleClient.connect(connOpts);
    // System.out.println("Connected");
    // System.out.println("Publishing message: "+content);
    // MqttMessage message = new MqttMessage(content.getBytes());
    // message.setQos(qos);
    // sampleClient.publish(topic, message);
    // System.out.println("Message published");
    // sampleClient.disconnect();
    // System.out.println("Disconnected");
    // System.exit(0);
    // } catch(MqttException me) {
    // System.out.println("reason "+me.getReasonCode());
    // System.out.println("msg "+me.getMessage());
    // System.out.println("loc "+me.getLocalizedMessage());
    // System.out.println("cause "+me.getCause());
    // System.out.println("excep "+me);
    // me.printStackTrace();
    // }
}

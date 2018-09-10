package com.microsoft.docs.iothub.samples;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.microsoft.azure.sdk.iot.device.DeviceClient;
import com.microsoft.azure.sdk.iot.device.IotHubClientProtocol;
import com.microsoft.azure.sdk.iot.device.IotHubEventCallback;
import com.microsoft.azure.sdk.iot.device.IotHubStatusCode;
import com.microsoft.azure.sdk.iot.device.Message;
import com.microsoft.docs.iothub.samples.telemetry.FuelTheft;
import com.microsoft.docs.iothub.samples.telemetry.LocationTelemetry;
import com.microsoft.docs.iothub.samples.telemetry.TelemetryDataPoint;

public class TrackingSimulator {
	/** The device connection string to authenticate the device with your IoT hub. Using the Azure CLI:
		az iot hub device-identity show-connection-string --hub-name {YourIoTHubName} --device-id MyJavaDevice --output table **/
	private static String connString = "#Your_Connection_String";

	// Using the AMQPS protocol to connect to IoT Hub
	private static IotHubClientProtocol protocol = IotHubClientProtocol.AMQPS;
	private static DeviceClient client;

	// Print the acknowledgement received from IoT Hub for the telemetry message sent.
	private static class EventCallback implements IotHubEventCallback {
		public void execute(IotHubStatusCode status, Object context) {
			System.out.println("IoT Hub responded to message with status: " + status.name());

			if (context != null) {
				synchronized (context) {
					context.notify();
				}
			}
		}
	}

	private static class MessageSender implements Runnable {
		public void run() {
			try {
				// Initialize the simulated telemetry.
				double minTemperature = 20;
				double minHumidity = 60;
				Random rand = new Random();
				int i = 0;

				while (i < 100000) {
					// Simulate telemetry.
					TelemetryDataPoint telemetryDataPoint = null;

					if (i % 2 == 0) {
						double currentLatitude = minTemperature + rand.nextDouble() * 15;
						double currentLongitude = minHumidity + rand.nextDouble() * 20;
						telemetryDataPoint = new LocationTelemetry();
						((LocationTelemetry) telemetryDataPoint).setLatitude(currentLatitude);
						((LocationTelemetry) telemetryDataPoint).setLongitude(currentLongitude);
						if (i % 4 == 0) {
							((LocationTelemetry) telemetryDataPoint).setLocation("At some Port");
						}
					} else {
						telemetryDataPoint = new FuelTheft();
					}

					// Add the telemetry to the message body as JSON.
					String msgStr = telemetryDataPoint.serialize();
					byte[] bytes = msgStr.getBytes(StandardCharsets.UTF_8);
					Message msg = new Message(bytes);
					msg.setContentEncoding("utf-8");
					msg.setContentType("application/json");

					System.out.println("Sending message string: " + msgStr);
					System.out.println("Sending message: " + msg);

					Object lockobj = new Object();

					// Send the message.
					EventCallback callback = new EventCallback();
					client.sendEventAsync(msg, callback, lockobj);

					synchronized (lockobj) {
						lockobj.wait();
					}
					Thread.sleep(1000);
					i++;
				}
			} catch (InterruptedException e) {
				System.out.println("Finished.");
			}
		}
	}

	public static void main(String[] args) throws IOException, URISyntaxException {

		// Connect to the IoT hub.
		client = new DeviceClient(connString, protocol);
		client.open();

		// Create new thread and start sending messages
		MessageSender sender = new MessageSender();
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(sender);

		// Stop the application.
		System.out.println("Press ENTER to exit.");
		System.in.read();
		executor.shutdownNow();
		client.closeNow();
	}
}

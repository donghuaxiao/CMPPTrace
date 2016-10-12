package com.ericsson;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapDumper;
import org.jnetpcap.packet.PcapPacket;

import com.ericsson.filter.CMPPSubmitPacketFilter;
import com.ericsson.filter.PacketFilter;
import com.ericsson.filter.TcpFilter;
import com.ericsson.writer.FileWriter;

public class TracerExecutor {

	/* ץ�� */
	private PacketCapture capture;
	
	/* �ѹ��˺�İ�д��ָ�����ļ� */
	private FileWriter fileWriter;
	
	/* ��������*/
	private PacketParser parser;
	
	private List<String> networkDevs;
	
	private String filter;
	
	private PacketFilter packetFilter;
	
	/* ����CMPP ���Ű����ļ��� */
	private String fileName;
	
	/* ����Ѿ����� CMPP Submit ��*/
	private BlockingQueue<PcapPacket> filterQueue = new LinkedBlockingQueue<>();
	
	/* ���Ҫд���ļ��İ� */
	private BlockingQueue<PcapPacket> storeQueue = new LinkedBlockingQueue<>();
	
	private ExecutorService services = new ThreadPoolExecutor(4, 8, 1, TimeUnit.HOURS, new LinkedBlockingQueue<Runnable>());
	
	private List<Pcap> pcapList = new ArrayList<>();
	
	private PcapDumper dumper;
	
	class ExitHandler extends Thread {
		@Override
		public void run() {
            System.out.println("CMPPTrace exited.");
            services.shutdown();
            parser.stop();
            fileWriter.stop();
            
		}
	}
	
	public TracerExecutor(List<String> networkDevs, String filter,  String fileName, PacketFilter packetFilter) {
		this.filter = filter;
		
		this.packetFilter =  packetFilter;
		
		this.networkDevs = networkDevs;
		
		this.fileName = fileName;
		
		Runtime.getRuntime().addShutdownHook( new ExitHandler());
	}
	
	public void start() {
		
		for (String dev : networkDevs) {
			PacketCapture capture = new PacketCapture(dev, filterQueue);
			if (this.filter != null) {
				capture.setFilter(filter);
			}
			//capture.setPacketFilter(  new CMPPSubmitPacketFilter() );
			capture.setPacketFilter( new TcpFilter());
			this.services.submit(capture);
			pcapList.add( capture.getPcap());
		}
		
		this.dumper = pcapList.get(0).dumpOpen(this.fileName);
		
		this.fileWriter = new FileWriter(storeQueue, dumper);
		
		this.parser = new PacketParser(filterQueue, storeQueue);
		if ( this.packetFilter != null) {
			this.parser.setPacketFilter(packetFilter);
		}
		
		this.fileWriter.start();
		this.parser.start();
		try {
			this.fileWriter.join();
			this.parser.join();
		} catch(InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
	public void stop() {
		this.services.shutdown();
		this.parser.stop();
		this.fileWriter.stop();
	}
	
}

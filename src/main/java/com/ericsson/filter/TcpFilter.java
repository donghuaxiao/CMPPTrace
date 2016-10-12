package com.ericsson.filter;

import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.protocol.tcpip.Tcp;

public class TcpFilter implements PacketFilter {

	Tcp tcp = new Tcp();

	public boolean isMatch(PcapPacket packet) {
		if (packet.hasHeader(tcp)) {
			return true;
		}
		return false;
	}
}

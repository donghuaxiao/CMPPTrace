package com.ericsson.protocol;

import java.nio.ByteBuffer;

import com.ericsson.util.Utils;


public class CMPPSubmit extends Request {

    private byte[] msgId = new byte[8];
    private byte pkTotal = 0;
    private byte pkNumber = 0;
    private byte registeredDelivery;
    private byte msgLevel;
    private String serviceId;
    private byte feeUserType;
    private String feeTerminalId; //琚璐圭敤鎴�
    
    private byte feeTerminalType;

    private byte tpPid;
    private byte tpUdhi;
    private byte msgFormat;

    private String msgSrc;

    private String feeType;

    private String feeCode;

    private String validTime;
    private String atTime;

    /*鍘熷彿鐮�*/
    private String srcId;

    private byte destUsrtl;

    private String[] destTerminalId;

    private byte destTerminalType;

    private byte msgLength;

    private String msgContent;

    private String linkId;

    public byte[] getMsgId() {
        return msgId;
    }
    
    public void setMsgId( byte[] msgId){
    	this.msgId = msgId;
    }

    public byte getPkTotal() {
        return pkTotal;
    }

    public void setPkTotal(byte pkTotal) {
        this.pkTotal = pkTotal;
    }

    public byte getPkNumber() {
        return pkNumber;
    }

    public void setPkNumber(byte pkNumber) {
        this.pkNumber = pkNumber;
    }

    public byte getRegisteredDelivery() {
        return registeredDelivery;
    }

    public void setRegisteredDelivery(byte registeredDelivery) {
        this.registeredDelivery = registeredDelivery;
    }

    public byte getMsgLevel() {
        return msgLevel;
    }

    public void setMsgLevel(byte msgLevel) {
        this.msgLevel = msgLevel;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public byte getFeeUserType() {
        return feeUserType;
    }

    public void setFeeUserType(byte feeUserType) {
        this.feeUserType = feeUserType;
    }

    public String getFeeTerminalId() {
        return feeTerminalId;
    }

    public void setFeeTerminalId(String feeTerminalId) {
        this.feeTerminalId = feeTerminalId;
    }

    public byte getFeeTerminalType() {
        return feeTerminalType;
    }

    public void setFeeTerminalType(byte feeTerminalType) {
        this.feeTerminalType = feeTerminalType;
    }
    

    public byte getTpPid() {
        return tpPid;
    }

    public void setTpPid(byte tpPid) {
        this.tpPid = tpPid;
    }

    public byte getTpUdhi() {
        return tpUdhi;
    }

    public void setTpUdhi(byte tpUdhi) {
        this.tpUdhi = tpUdhi;
    }

    public byte getMsgFormat() {
        return msgFormat;
    }

    public void setMsgFormat(byte msgFormat) {
        this.msgFormat = msgFormat;
    }

    public String getMsgSrc() {
        return msgSrc;
    }

    public void setMsgSrc(String msgSrc) {
        this.msgSrc = msgSrc;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getFeeCode() {
        return feeCode;
    }

    public void setFeeCode(String feeCode) {
        this.feeCode = feeCode;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public String getAtTime() {
        return atTime;
    }

    public void setAtTime(String atTime) {
        this.atTime = atTime;
    }

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public byte getDestUsrtl() {
        return destUsrtl;
    }

    public void setDestUsrtl(byte destUsrtl) {
        this.destUsrtl = destUsrtl;
    }

    public String[] getDestTerminalId() {
        return destTerminalId;
    }

    public void setDestTerminalId(String[] destTerminalId) {
        this.destTerminalId = destTerminalId;
    }

    public byte getDestTerminalType() {
        return destTerminalType;
    }

    public void setDestTerminalType(byte destTerminalType) {
        this.destTerminalType = destTerminalType;
    }

    public byte getMsgLength() {
        return msgLength;
    }

    public void setMsgLength(byte msgLength) {
        this.msgLength = msgLength;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String reserve) {
        this.linkId = reserve;
    }

	@Override
	public void setBody(ByteBuffer buffer) {
		buffer.get(msgId);
		setPkTotal(buffer.get());
		setPkNumber(buffer.get());
		setRegisteredDelivery(buffer.get());
		setMsgLevel( buffer.get() );
		
		setServiceId(Utils.getStringFromByteBuffer(buffer, 10) );
		setFeeUserType( buffer.get());
		setFeeTerminalId(Utils.getStringFromByteBuffer(buffer, 32));
                setFeeTerminalType(buffer.get());
                
		setTpPid(buffer.get());
		setTpUdhi(buffer.get());
		setMsgFormat(buffer.get());
		setMsgSrc(Utils.getStringFromByteBuffer(buffer, 6));
		setFeeType(Utils.getStringFromByteBuffer(buffer, 2));
		setFeeCode( Utils.getStringFromByteBuffer(buffer, 6));
                
		setValidTime(Utils.getStringFromByteBuffer(buffer, 17));
		setAtTime(Utils.getStringFromByteBuffer(buffer, 17));
		setSrcId(Utils.getStringFromByteBuffer(buffer, 21));
                
		byte count = buffer.get();
		setDestUsrtl(count);
		String destTerminals = Utils.getStringFromByteBuffer(buffer, 32 * count);
		setDestTerminalId( Utils.getDestTerminalId(destTerminals));
		setDestTerminalType(buffer.get());
		byte length = buffer.get();
		setMsgLength(length);
		setMsgContent(Utils.getStringFromByteBuffer(buffer, length));
		setLinkId(Utils.getStringFromByteBuffer(buffer, 20));
	}

	@Override
	public ByteBuffer getBody() {
		ByteBuffer buffer = ByteBuffer.allocate(this.header.getPacketLength() - CMPP.CMPP_HEAD_LEN);
		buffer.put(this.msgId);
		buffer.put(this.getPkTotal());
		buffer.put(this.getPkNumber());
		buffer.put(this.getRegisteredDelivery());
		buffer.put(this.getMsgLevel());
		
		buffer.put( Utils.toBytes(getServiceId(), 10));
		buffer.put( getFeeUserType());
		buffer.put( Utils.toBytes(getFeeTerminalId(), 32));
		buffer.put( getFeeTerminalType());
		
		buffer.put( getTpPid());
		buffer.put( getTpUdhi());
		buffer.put(getMsgFormat());
		buffer.put( Utils.toBytes(getMsgSrc(), 6));
		buffer.put( Utils.toBytes(getFeeType(), 2));
		
		buffer.put( Utils.toBytes(getFeeCode(), 6));
		buffer.put( Utils.toBytes(getValidTime(), 17));
		buffer.put( Utils.toBytes(getAtTime(), 17));
		buffer.put( Utils.toBytes(getSrcId(), 21));
		
		buffer.put(getDestUsrtl());
		for ( String terminalId: getDestTerminalId()) {
			buffer.put( Utils.toBytes(terminalId, 32));
		}
		
		buffer.put(getDestTerminalType());
		buffer.put( getMsgLength());
		buffer.put( getMsgContent().getBytes());
		buffer.put( getLinkId().getBytes());
		
		return buffer;
	}

	@Override
	public void setData(ByteBuffer buffer) {
		this.header.setData(buffer);
		this.setBody(buffer);
	}

	@Override
	public ByteBuffer getData() {
		ByteBuffer buffer = this.header.getData();
		buffer.put( this.getBody().array());
		return buffer;
	}

}

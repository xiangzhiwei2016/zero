package com.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 创建日期：2017年11月6日 下午5:50:07
 * <b>Copyright 2017 上海量投网络科技有限公司 All Rights Reserved</b>
 * 
 * @author  xzw
 * @since   1.0
 * @version 1.0
*/
@Entity
@Table(name = "t_message", uniqueConstraints = { @UniqueConstraint(columnNames = { "msg_id" }) })
public class Message implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="sequence")
	@SequenceGenerator(name="sequence",sequenceName="seq_t_message")  
    @Column(name = "msg_id")
    private Long msgID;

    //消息类型
    @Column(name = "msg_type",  length = 1)
    private String msgType;

    //消息标题
    @Column(name = "msg_title",length = 100)
    private String msgTitle;

    //消息内容
    @Column(name = "msg_body", length = 1000)
    private String msgBody;
    //消息内容
    
    @Column(name = "message_code", length = 1000)
    private String messageCode;
    
    @Column(name = "failure_time",length = 1000)
    private String failureTime;
    
    //接收人类型
    @Column(name = "msg_receiver_type", length = 10)
    private String msgReceiverType;
   
    //接收人
    @Column(name = "msg_receiver", length = 2000)
    private String msgReceiver;
    
    // 操作员
    @Column(name = "operator_id", length = 20)
    @Length(min = 1, max = 20)
    private String operatorID;

    // 操作日期
    @Column(name = "operate_date", length = 8)
    @Length(min = 8, max = 8)
    private String operateDate;

    // 操作时间
    @Column(name = "operate_time", length = 8)
    @Length(min = 6, max = 8)
    private String operateTime;

    public Long getMsgID()
    {
        return msgID;
    }

    public void setMsgID(Long msgID)
    {
        this.msgID = msgID;
    }

    public String getMsgType()
    {
        return msgType;
    }

    public void setMsgType(String msgType)
    {
        this.msgType = msgType;
    }

    public String getMsgTitle()
    {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle)
    {
        this.msgTitle = msgTitle;
    }

    public String getMsgBody()
    {
        return msgBody;
    }

    public void setMsgBody(String msgBody)
    {
        this.msgBody = msgBody;
    }

    public String getOperatorID()
    {
        return operatorID;
    }

    public void setOperatorID(String operatorID)
    {
        this.operatorID = operatorID;
    }

    public String getOperateDate()
    {
        return operateDate;
    }

    public void setOperateDate(String operateDate)
    {
        this.operateDate = operateDate;
    }

    public String getOperateTime()
    {
        return operateTime;
    }

    public void setOperateTime(String operateTime)
    {
        this.operateTime = operateTime;
    }

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getFailureTime() {
		return failureTime;
	}

	public void setFailureTime(String failureTime) {
		this.failureTime = failureTime;
	}

	public String getMsgReceiverType() {
		return msgReceiverType;
	}

	public void setMsgReceiverType(String msgReceiverType) {
		this.msgReceiverType = msgReceiverType;
	}

	public String getMsgReceiver() {
		return msgReceiver;
	}

	public void setMsgReceiver(String msgReceiver) {
		this.msgReceiver = msgReceiver;
	}

	@Override
	public String toString() {
		return "Message [msgID=" + msgID + ", msgType=" + msgType + ", msgTitle=" + msgTitle + ", msgBody=" + msgBody
				+ ", messageCode=" + messageCode + ", failureTime=" + failureTime + ", msgReceiverType="
				+ msgReceiverType + ", msgReceiver=" + msgReceiver + ", operatorID=" + operatorID + ", operateDate="
				+ operateDate + ", operateTime=" + operateTime + "]";
	}
    
}

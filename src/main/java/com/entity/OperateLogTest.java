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

/**
 * 
 * @Description:操作日志
 * @author xzw
 * @date 20170227 下午2:49:43
 * 
 */
@Entity
@Table(name = "t_dh_operate_log_test", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class OperateLogTest implements Serializable {

	private static final long serialVersionUID = 1L;

	// srchcolumns:jobName,status,operateDate
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence")
	@SequenceGenerator(name = "sequence", sequenceName = "seq_t_operate_log_test")
	@Column(name = "id")
	private Long id;
	/**
	 * 任务名称
	 */
	@Column(name = "job_name", length = 50)
	private String jobName;
	/**
	 * 任务分组
	 */

	@Column(name = "job_group", length = 50)
	private String jobGroup;
	/**
	 * 状�??
	 */
	// select
	@Column(name = "status", length = 4)
	private String status;

	/**
	 * 操作日期 
	 */
	// date
	@Column(name = "operate_date", length = 30)
	private String operateDate;

	/**
	 * 操作时间
	 */
	@Column(name = "operate_time", length = 30)
	private String operateTime;

	/**
	 * 文件名称
	 */
	@Column(name = "file_name", length = 30)
	private String fileName;

	/**
	 * 文件路径
	 */
	@Column(name = "filePath", length = 2000)
	private String filePath;

	/**
	 * 总记�??
	 */
	@Column(name = "total_count", length = 50)
	private String totalCount;
	/**
	 * 实际导入记录
	 */
	@Column(name = "real_count", length = 50)
	private String realCount;

	/**
	 * 备注
	 */
	@Column(name = "remark", length = 200)
	private String remark;
	
	public OperateLogTest() {
		super();
	}

	public OperateLogTest(String jobName, String jobGroup, String status, String operateDate, String operateTime,
			String fileName, String filePath, String totalCount, String realCount, String remark) {
		super();
		this.jobName = jobName;
		this.jobGroup = jobGroup;
		this.status = status;
		this.operateDate = operateDate;
		this.operateTime = operateTime;
		this.fileName = fileName;
		this.filePath = filePath;
		this.totalCount = totalCount;
		this.realCount = realCount;
		this.remark = remark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getRealCount() {
		return realCount;
	}

	public void setRealCount(String realCount) {
		this.realCount = realCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "OperateLogTest [id=" + id + ", jobName=" + jobName + ", jobGroup=" + jobGroup + ", status=" + status
				+ ", operateDate=" + operateDate + ", operateTime=" + operateTime + ", fileName=" + fileName
				+ ", filePath=" + filePath + ", totalCount=" + totalCount + ", realCount=" + realCount + ", remark="
				+ remark + "]";
	}
	
}

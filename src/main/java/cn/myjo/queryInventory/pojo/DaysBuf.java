package cn.myjo.queryInventory.pojo;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DaysBuf {
	private String itemId;
	private @Past Date startDays;
	private Date endDays;
	private @Size(min=1,max=7) int days; 
}
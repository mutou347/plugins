import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import logo from './logo.svg';
import './App.css';
import './flag.css'
import 'antd/dist/antd.css';

import $ from 'jquery';
//import { Modal, List, Button, WhiteSpace, WingBlank } from 'antd-mobile';
import 'antd/lib/button/style';
import { Slider, InputNumber, Row, Col,Form, Icon,Input,Modal, Button,Table, Divider,Drawer,Radio,Tooltip,BackTop} from 'antd';

const RadioGroup = Radio.Group;
const { TextArea } = Input;
//设定旗子旗子
//绿旗
var green={height: '17px',width: '1px',paddingLeft: '17px',display: 'inline-block',visibility: 'visible'
  ,background: 'url(qizi.png) no-repeat -120px -207px'}
//紫旗
var purple={height: '17px',width: '1px',paddingLeft: '17px',display: 'inline-block',visibility: 'visible'
  ,background: 'url(qizi.png) no-repeat -80px -207px'}
//灰旗
var gray={height: '17px',width: '1px',paddingLeft: '17px',display: 'inline-block',visibility: 'visible'
  ,background: 'url(qizi.png) no-repeat -176px -176px'}
//红旗
var red={height: '17px',width: '1px',paddingLeft: '17px',display: 'inline-block',visibility: 'visible'
      ,background:'url(qizi.png) no-repeat -100px -207px'}
//蓝旗
var blue={height: '17px',width: '1px',paddingLeft: '17px',display: 'inline-block',visibility: 'visible'
      ,background: 'url(qizi.png) no-repeat -60px -207px'}
//黄旗
var yellow={height: '17px',width: '1px',paddingLeft: '17px',display: 'inline-block',visibility: 'visible'
        ,background: 'url(qizi.png) no-repeat -140px -207px'}
// require("./flag.css");
//状态图片展示
class StatusImg extends Component{
  state = {
     visible: false,//是否显示抽屉
     value:this.props.celsius,       //旗子的属性
     textareaValue:this.props.memo  //备注的内容
   };
  constructor(status){
    super(status);
  }
  // 修改旗子颜色的方法
  onChange = (e) => {
    this.setState({
      value: e.target.value
    });
  }

  // 修改多行文本框的方法
  handleTextareaChange = (e) =>{
    this.setState({
      textareaValue:e.target.value
    });
  }
  //显示抽屉
  showDrawer = () => {
    this.setState({
      visible: true
    });
    };
//关闭抽屉
 onClose = () => {

  this.setState({
    visible: false
  });
  console.log("更新完毕！");
 };
 handleClickUpdate = () => {
   var str=JSON.stringify({'flag':this.state.value,'memo':this.state.textareaValue,'orderId':this.props.orderId});
   $.ajax({
  	 url:"http://101.37.86.8/updateDemo",
  	 //url:"http://127.0.0.1/updateDemo",
  //	 xhrFields: {
  //	  withCredentials: true // 设置运行跨域操作
  //	 },

  	 //sychro:"true",
  	 //dataType:'json',

     crossDomain: true,
  	 contentType : 'application/json;charset=GBK',
  	 method:"POST",
  	 data:str,   //将 旗帜状态|备注|订单编号 传递到后端
  	 success:function(){
       alert("更新成功");
  		console.log("更新成功");
  	 }
   });
   window.location.reload();
   this.setState({
     visible: false
   });
 };
  render(){
        return <div>
                 <Tooltip placement="topLeft" title={this.state.textareaValue}>
                    <i  style={this.props.celsius==0? green:
                            (this.props.celsius==1?purple:
                             this.props.celsius==3?red:
                             this.props.celsius==4?blue:
                             this.props.celsius==5?yellow:
                             gray)}
                            onClick={this.showDrawer}/>
                  </Tooltip>
                  <Drawer
                    title="更换旗子备注"
                    placement="right"
                    closable={false}
                    onClose={this.onClose}
                    visible={this.state.visible}
                  >
                    <Row>
                     {/* 选择旗子 */}
                     <RadioGroup onChange={this.onChange} value={this.state.value}>
                       <Radio value={0}><i style={green}/></Radio>
                       <Radio value={1}><i style={purple}/></Radio>
                       <Radio value={2}><i style={gray}/></Radio>
                       <Radio value={3}><i style={red}/></Radio>
                       <Radio value={4}><i style={blue}/></Radio>
                       <Radio value={5}><i style={yellow}/></Radio>
                     </RadioGroup>
                   </Row>
                   <br/>
                   <Row>
                     {/*修改备注*/}
                     添加备注：<TextArea rows={15}
                                        placeholder="添加旗子备注"
                                        defaultValue={this.state.textareaValue}
                                        onChange={this.handleTextareaChange}/>
                   </Row>
                   <br/>
                   <Row>
                     <Button onClick={this.handleClickUpdate} onMouseUp={this.props.changValue}>UPDATE</Button>
                   </Row>
                  </Drawer>
                </div>;
  }
}

//app启用组件
class App extends Component {
  constructor(){
    super();
    this.state={//memoData：机器修改的商家编码。   datarest:人工修改的商家
      memoData:[],datarest:[],visible:false
    };
  }
  componentWillMount(){
    this.ChangValue();
  }
  ChangValue(){
    console.log("开始添加：")
    $.ajax({
      url:"http://101.37.86.8/getAll",
      //url:"http://127.0.0.1/getAll",
      async:false,
      type:"GET",
      dataType:'json',
      success:function(resData){
        console.log(resData);
        this.setState({
          datarest:resData[0],
          memoData:resData[1]
        });
      }.bind(this)
    });
  }
  render(){
    const { Column, ColumnGroup } = Table;
    const datamemo = this.state.memoData;
    const datarest=this.state.datarest;
    return (
      <div style={{width:750}}>
      {/* 返回顶部 */}
		  {/*<BackTop />*/}
      <Table
        pagination={{ 
			pageSize: 5
		}}
		size="small"
        expandedRowRender={record => <div style={{margin: 0,padding:0}}>
                                          <Icon type="smile-o" /><br/>
                                          <p style={{ margin: 0,textIndent:'2em',lineHeight: '2em',color:'#8c8c8c'}}>
                                              <b>{record.memo}</b>
                                          </p>
                                      </div>}
        dataSource={datamemo} size="small"
        title={()=>'缺货通知展示表格'}
        footer={() => '请核实确认该买家为缺货后,再进行处理！'}>
			<Column
			  title="订单编号"
			  dataIndex="orderId"
			  align="center"
			  style={{ margin: 0,padding:0}}
			/>
			<Column
			  title="商家编码"
			  dataIndex="itemId"
			  align="center"
			/>
			<Column
			  title="付款时间"
			  dataIndex="payTime"
			  align="center"
			   style={{ margin: 0,padding:0}}
			  render={(text,record)=>(
				 <span>
				   <a href="javascript:;">
					   {
						   (
							   parseInt(new Date(record.payTime).getMonth()+1)<10?"0"+
							   parseInt(new Date(record.payTime).getMonth()+1):
							   parseInt(new Date(record.payTime).getMonth()+1)
						   )+"-"+
						   (
						   new Date(record.payTime).getDate()<10?"0"+new Date(record.payTime).getDate():new Date(record.payTime).getDate()
						   )+" "+
						   (
						   new Date(record.payTime).getHours()<10?"0"+new Date(record.payTime).getHours():+new Date(record.payTime).getHours()
						   )+":"+
						   (
						   new Date(record.payTime).getMinutes()<10?"0"+new Date(record.payTime).getMinutes():new Date(record.payTime).getMinutes()
						   )
					   }
					  </a>
				 </span>
				)}
			/>
			<Column
			  title="缺货时间"
			  dataIndex="performance_time"
			  align="center"
			  style={{ margin: 0,padding:0}}
			  render={(text,record)=>(
				 <span>
				   <a href="javascript:;">
					   {
						   (
							   parseInt(new Date(record.performance_time).getMonth()+1)<10?"0"+
							   parseInt(new Date(record.performance_time).getMonth()+1):
							   parseInt(new Date(record.performance_time).getMonth()+1)
						   )+"-"+
						   (
						   new Date(record.performance_time).getDate()<10?"0"+new Date(record.performance_time).getDate():new Date(record.performance_time).getDate()
						   )+" "+
						   (
						   new Date(record.performance_time).getHours()<10?"0"+new Date(record.performance_time).getHours():+new Date(record.performance_time).getHours()
						   )+":"+
						   (
						   new Date(record.performance_time).getMinutes()<10?"0"+new Date(record.performance_time).getMinutes():new Date(record.performance_time).getMinutes()
						   )
					   }
					  </a>
				 </span>
			  )}
			/>
			<Column
			  title="短信"
			  dataIndex="note"
			  align="center"
			  style={{ margin: 0,padding:0}}
			  render={(text,record)=><span>{record.note?"已发送":"未发送"}</span>}
			/>
			<Column
			  title="临时"
			  dataIndex="temporary"
			  align="center"
			  style={{ margin: 0,padding:0}}
			  render={(text,record)=><span>{record.temporary?"已添加":"未添加"}</span>}
			/>
			<Column
			  title="买家ID"
			  dataIndex="buyer"
			  align="center"
			  style={{ margin: 0,padding:0}}
			/>
			<Column
			  title="实付金额"
			  dataIndex="pay_amount"
			  align="center"
			  style={{ margin: 0,padding:0}}
			/>
			<Column
			  title="备注"
			  dataIndex="flag"
			  align="center"
			  style={{ margin: 0,padding:0}}
			  render={(text, record) => (
				<StatusImg celsius={record.flag} orderId={record.orderId} memo={record.memo}/>
			)}
        />
      </Table>
    <ModalApp changValue={this.ChangValue.bind(this)}/>
    <Table dataSource={datarest} pagination={{ pageSize: 5 }}
          title={()=>'人工添加临时 表格'}>
          <Column
            title="商家编码"
            dataIndex="itemId"
          />
          <Column
            title="设置时间"
            dataIndex="startDays"
			render={(text,record)=>(
			 <span>
               <a href="javascript:;">
                   {(new Date(record.startDays).getMonth()+1)+"月"+
					   new Date(record.startDays).getDate()+"日"+new Date(record.startDays).getHours()+"时"+
					   new Date(record.startDays).getMinutes()+"分"
                   }
                  </a>
             </span>
			)}
          />
          <Column
            title="结束时间"
            dataIndex="endDays"
			render={(text,record)=>(
			 <span>
               <a href="javascript:;">
                   {(new Date(record.endDays).getMonth()+1)+"月"+
					   new Date(record.endDays).getDate()+"日"+new Date(record.endDays).getHours()+"时"+
					   new Date(record.endDays).getMinutes()+"分"
                   }
                  </a>
             </span>
			)}
          />
          <Column
            title="时间长度"
            dataIndex="days"
            render={(text,record)=>(
                <p>{record.days} 天</p>
              )}
          />
          <Column
            title="剩余时间"
            key="remaining"
            render={(text, record) => (
             <span>
               <a href="javascript:;">
                   {
                      parseInt((new Date(record.endDays)-(new Date()).valueOf()) / (1000 * 60 * 60 * 24))>=0 &&
                      parseInt((new Date(record.endDays)-(new Date()).valueOf()) / (1000 * 60 * 60 ) % 24)>=0 &&
                      parseInt((new Date(record.endDays)-(new Date()).valueOf()) / (1000 * 60 )  % 60)>=0
                     ?parseInt((new Date(record.endDays)-(new Date()).valueOf()) / (1000 * 60 * 60 * 24))+"天"+
                      parseInt((new Date(record.endDays)-(new Date()).valueOf()) / (1000 * 60 * 60 ) % 24)+"时"+
                      parseInt((new Date(record.endDays)-(new Date()).valueOf()) / (1000 * 60 )  % 60)+"分"
                     :"等待中"
                   }
                  </a>
             </span>
           )}
          />
     {/* <Column
            title="Action"
            key="action"
            render={(text, record) => (
              <span>
                <a>Delete</a>
              </span>
            )}
          /> */
        }
        </Table>
    </div>
  );}
}
//模态框组件
class ModalApp extends Component {
  state = { visible: false }
  showModal = () => {
    this.setState({
      visible: true,
    });
  }
  handleOk = (e) => {
    console.log(e);
    this.setState({
      visible: false,
    });
    this.props.changValue();
  }
  handleCancel = (e) => {
    console.log(e);
    this.setState({
      visible: false,
    });
    this.props.changValue();
  }
  render() {
    return (
      <div>
        <Button type="primary" onClick={this.showModal} size="small" ghost="true">修改商家编码</Button>
        <Modal title="修改商家编码" visible={this.state.visible} onOk={this.handleOk} onCancel={this.handleCancel}>
            <div id="modelDiv">
              <WrappedHorizontalLoginForm/>
            </div>
        </Modal>
      </div>
    );
  }
}
const FormItem = Form.Item;
function hasErrors(fieldsError) {
  return Object.keys(fieldsError).some(field => fieldsError[field]);
}

//表单组件
class HorizontalLoginForm extends Component{
  componentDidMount() {
    this.props.form.validateFields();
  }
  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      values.days=this.state.inputValue;
      if (!err) {
        console.log('Received values of form: ', values);
        $.ajax({
         url:"http://101.37.86.8/updateOuterId",
		 // url:"http://127.0.0.1/updateOuterId",
          async:false,
          type:"GET",
          data: values,
          dataType:'text',
          success:function(resData){
            this.setState({
              data:resData
            });
          }.bind(this)
        })
      }
    });
	window.location.reload();
  }
  state = {
    inputValue: 1
  };
  onChange = value => {
    this.setState({
      inputValue: value
    });
  };
  render() {
    const { getFieldDecorator, getFieldsError, getFieldError, isFieldTouched } = this.props.form;
    const ItemId = isFieldTouched('itemId') && getFieldError('itemId');
//  const passwordError = isFieldTouched('password') && getFieldError('password');
//  const daysError = isFieldTouched('days') && getFieldError('days');
    const formItemLayout = {
      labelCol: { span: 3},
      wrapperCol: { span: 21 }
    };
    return (
      <div>
      <Form onSubmit={this.handleSubmit}>
        <Row>
          <FormItem
            validateStatus={ItemId ? 'error' : ''}
            help={ItemId || ''}
          >
            {getFieldDecorator('ItemId', {
              rules: [{
                required: true,
                message: 'Please input your ItemId!',
                pattern: '^[\\w]{6}-[\\w\\W]{1,3}-[\\w\\W]{1,3}|[\\w]{6}-[\\w\\W]{1,3}$'
              }],
            })(
              <Input prefix={<Icon type="taobao" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="商家编码" />
            )}
          </FormItem>
      </Row>
        <FormItem {...formItemLayout} label="持续时间">
            {getFieldDecorator("days")(
              <Row>
                <Col span={18}>
                  <Slider min={1} max={7}
                    onChange={this.onChange} value={this.state.inputValue} />
                </Col>
                <Col span={5}>
                  <InputNumber
                    min={1}
                    max={7}
                    style={{ marginLeft: 16 }}
                    value={this.state.inputValue}
                    onChange={this.onChange}
                  />
                </Col>
              </Row>
            )}
        </FormItem>
        <FormItem>
          <Button
            type="primary"
            htmlType="submit"
            disabled={hasErrors(getFieldsError())}
            wrapperCol={{ span: 15, offset: 2 }}>提交</Button>
        </FormItem>
      </Form>
      <div><p>请确保<span style={{color:'#1088e9'}}>商家编码准确无误</span>后,再点击提交</p></div>
    </div>
    );
  }
}
const WrappedHorizontalLoginForm = Form.create()(HorizontalLoginForm);
export default App;

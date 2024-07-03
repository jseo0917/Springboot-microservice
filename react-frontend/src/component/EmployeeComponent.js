import React, { Component } from 'react'
import EmployeeService from '../service/EmployeeService';

export default class EmployeeComponent extends Component {

  constructor(props) {
    super(props);    

    this.state = {
        employeeDto: {},
        departmentDto: {},
        organizationDto: {}
    }
  }

  componentDidMount(){
    EmployeeService.getEmployee().then((response) => {
        this.setState({employeeDto : response.data.employeeDto})
        this.setState({departmentDto: response.data.departmentDto})
        this.setState({organizationDto: response.data.organizationDto})

        console.log(this.state.employeeDto)
        console.log(this.state.departmentDto)
        console.log(this.state.organizationDto)
    })
  }

  render() {
    return (
      <div className='container card offset-md-3'>
        <h3 className='text-center card=header'> View Employee Details</h3>
        <div className='card-body'>
            <div className='row'>
                <p><strong>Employee first Name: </strong> {this.state.employeeDto.firstName}</p>
                <p><strong>Employee Last Name: </strong> {this.state.employeeDto.lastName}</p>
            </div>
        </div>
        <h3 className='text-center card=header'> View Department Details</h3>
        <div className='card-body'>
            <div className='row'>
                <p><strong>Department: </strong> {this.state.departmentDto.departmentName}</p>                
            </div>
        </div>
      </div>
    )
  }
}

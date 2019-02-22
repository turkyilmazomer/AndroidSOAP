using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebServiceExample.models
{
    public class Employee
    {

        public Employee(int employeeid,string title) {
            this.EmployeeId = employeeid;
            this.Title = title;
        }


        public int EmployeeId { get; set; }

        public string Title { get; set; }
    }
}
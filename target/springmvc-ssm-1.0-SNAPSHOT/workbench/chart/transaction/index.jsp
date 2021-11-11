<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
//	String id = request.getParameter("id");
//	String fullname = request.getParameter("fullname");
//	String appellation = request.getParameter("appellation");
//	String owner = request.getParameter("owner");
//	String company = request.getParameter("company");

    System.out.println(basePath);
%>
<!DOCTYPE html>
<html>
<head>
    <base href=<%=basePath%>>
    <meta charset="UTF-8">
</head>
<script src="workbench/ECharts/echarts.min.js"></script>
<script src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript">

    $(function () {
        getCharts();
        
    })
    
    function getCharts() {
        var myChart = echarts.init(document.getElementById('main'));


        $.ajax({
            url:"tran/getChars.do",
            type:"get",
            dataType:"json",
            success:function (data) {
                 option = {
                    title: {
                        text: '交易漏斗图'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c}%'
                    },
                    toolbox: {
                        feature: {
                            dataView: { readOnly: false },
                            restore: {},
                            saveAsImage: {}
                        }
                    },
                    legend: {
                        data: ['Show', 'Click', 'Visit', 'Inquiry', 'Order']
                    },
                    series: [
                        {
                            name: 'Funnel',
                            type: 'funnel',
                            left: '10%',
                            top: 60,
                            bottom: 60,
                            width: '80%',
                            min: 0,
                            max: data.total,
                            minSize: '0%',
                            maxSize: '100%',
                            sort: 'descending',
                            gap: 2,
                            label: {
                                show: true,
                                position: 'inside'
                            },
                            labelLine: {
                                length: 10,
                                lineStyle: {
                                    width: 1,
                                    type: 'solid'
                                }
                            },
                            itemStyle: {
                                borderColor: '#fff',
                                borderWidth: 1
                            },
                            emphasis: {
                                label: {
                                    fontSize: 20
                                }
                            },
                            data:
                                data.dataList
                               /* { value: 60, name: 'Visit' },
                                { value: 40, name: 'Inquiry' },
                                { value: 20, name: 'Order' },
                                { value: 80, name: 'Click' },
                                { value: 100, name: 'Show' }*/

                        }
                    ]
                };

                myChart.setOption(option);
            }
        })



    }
</script>
<body>

<div id="main" style="width: 600px;height: 400px;">

</div>
</body>
</html>
# language: zh-CN
@api-login
功能: 订单

  场景: 订单列表
    假如存在如下订单:
      | code  | productName | total | recipientName | status        |
      | SN001 | 电脑          | 19999 | 张三            | toBeDelivered |
    当API查询订单时
    那么返回如下订单
    """
      [{
        "code": "SN001",
        "productName": "电脑",
        "total": 19999,
        "status": "toBeDelivered"
      }]
    """

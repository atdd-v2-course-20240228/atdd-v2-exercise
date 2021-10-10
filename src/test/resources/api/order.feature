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

  场景: 订单详情 - 无物流
    假如存在如下订单:
      | code  | productName | total | recipientName | recipientMobile | recipientAddress | status     |
      | SN001 | 电脑          | 19999 | 张三            | 13085901735     | 上海市长宁区           | delivering |
    当API查询订单"SN001"详情时
    那么返回如下订单
    """
      {
        "code": "SN001",
        "productName": "电脑",
        "total": 19999,
        "recipientName": "张三",
        "recipientMobile": "13085901735",
        "recipientAddress": "上海市长宁区",
        "status": "delivering"
      }
    """

  场景: 订单发货
    假如存在如下订单:
      | code  | productName | total | recipientName | recipientMobile | recipientAddress | status        |
      | SN001 | 电脑          | 19999 | 张三            | 13085901735     | 上海市长宁区           | toBeDelivered |
    当通过API发货订单"SN001"，快递单号为"SF001"
    那么订单"SN001"已发货，快递单号为"SF001"


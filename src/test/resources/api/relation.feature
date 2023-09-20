# language: zh-CN
@api-login
功能: 关联数据

  场景: 关联数据
    假如存在"学校":
      | name | province | city     | createdAt  | code  |
      | PKU  | Beijing  | Haidian  | 1898-07-03 | 10001 |
      | ZJU  | Zhejiang | Hangzhou | 1897-05-21 | 10335 |
    假如存在"老师":
      | name  | gender | birthdate  | degree   |
      | tom   | male   | 1990-01-01 | master   |
      | jerry | male   | 1991-02-01 | bachelor |
    假如存在"班级":
      | name   | createdAt  |
      | classA | 2022-09-01 |
    假如存在"学生":
      | name     | chinese | english | math | bonus |
      | Zhangsan | 90      | 80      | 70   | 10    |
#    那么等待100000秒
    那么API"/score"应为:
    """
    code: 200
    """

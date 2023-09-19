# language: zh-CN
@api-login
功能: 关联数据

  场景: 关联数据
    假如存在"学校":
      | name | address |
      | PKU  | beijing |
      | THU  | haidian |
    假如存在"老师":
      | name |
      | PKU  |
      | THU  |
    假如存在"班级":
      | name   |
      | classA |
    假如存在"学生":
      | name | chinese | english | math | bonus |
      | Tom  | 90      | 80      | 70   | 10    |
#    那么等待100000秒
    那么API"/score"应为:
    """
    code: 200
    """

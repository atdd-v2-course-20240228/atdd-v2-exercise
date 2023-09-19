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

#      第一中学
#        班级:    | A         | B        | C    | D    | E    |
#               1 | 学校:     | 第一中学 | *    | *    | *    |
#               2 | 班级总数: | 1        | *    | *    | *    |
#               3 | 学生总数: | 1        | *    | *    | *    |
#               5 | 班级      | 班主任   | 语文 | 英语 | 数学 |
#               6 | 一年1班   | tomas    | 1    | 1    | 1    |
#
#        一年1班: | A    | B    | C    | D    | E    | F    |
#               1 | 姓名 | 语文 | 英语 | 数学 | 加分 | 总分 |
#               2 | tom  | 1    | 1    | 1    | 1    | 4    |
#
#      第二中学
#        一年2班: | A    | B    | C    | D    | E    | F    |
#               1 | 姓名 | 语文 | 英语 | 数学 | 加分 | 总分 |
#               2 | jere | 2    | 2    | 2    | 2    | 8    |

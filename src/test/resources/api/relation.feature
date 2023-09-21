# language: zh-CN
@api-login
功能: 关联数据

  场景: 关联数据
    假如存在"学校":
      | name |
      | PKU  |
      | ZJU  |
    假如存在"老师":
      | name  |
      | tom   |
      | jerry |
    假如存在"班级":
      | name   | school.name |
      | classA | PKU         |
      | classB | PKU         |
      | class1 | ZJU         |
      | class2 | ZJU         |
    假如存在"学生":
      | name     | clazz.name |
      | Zhangsan | classA     |
      | Lisi     | classA     |
      | Wangwu   | classB     |
      | Zhaoliu  | classB     |
    那么API"/score"应为:
    """
    body.unzip: {
      PKU.xlsx: {
        班级: | A     | B   |
            1 | 学校: | PKU |

        classA: | A        |
            1   | 姓名     |
            2   | Zhangsan |
      }
    }
    """

  场景: 总分
    假如存在"学生":
      | name     | chinese | english | math | bonus |
      | Zhangsan | 90      | 80      | 70   | 8     |
    那么API"/score"应为:
    """
    成绩单: | A        | F          |
        1   | 姓名     | 总分       |
        2   | Zhangsan | 90+80+70+8 |
    """

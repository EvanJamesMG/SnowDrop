import sys
import os

current_directory = os.path.dirname(os.path.abspath(__file__))
print(current_directory)

import jieba
import jieba.analyse
from optparse import OptionParser
import xlrd
import xlwt

if __name__ == '__main__':

    file_name = "Input.txt"
    topK = 10
    withWeight = True
    key_list = []

    ##原始输入文档
    data_excel = xlrd.open_workbook(current_directory + '\\File\\Input.xlsx')
    table = data_excel.sheets()[0]
    n_rows = table.nrows
    n_cols = table.ncols

    ##筛选关键字文档
    key_word_excel_list = xlrd.open_workbook(current_directory + '\\File\\Key_word.xlsx')
    key_word_table = key_word_excel_list.sheets()[0]
    key_word_n_rows = key_word_table.nrows
    key_word_n_cols = key_word_table.ncols

    ##筛选后的输出文档
    workbook = xlwt.Workbook()
    worksheet = workbook.add_sheet('Sheet1')

    for i in range(1,key_word_n_rows):
        key_word_row_col_data = key_word_table.cell_value(rowx=i, colx=0)
        key_list.append(key_word_row_col_data)

    worksheet.write(0, 0, "关键字")
    worksheet.write(0, 1, "用户问题")
    worksheet.write(0, 2, "客服回答")
    inc_index = 1
    for i in range(1, n_rows):
        row_col_data_user_question = table.cell_value(rowx=i, colx=0)
        row_col_data_answer = table.cell_value(rowx=i, colx=1)
        jieba.analyse.set_idf_path(current_directory + '\\File\\idf.txt.big')
        tags = jieba.analyse.extract_tags(row_col_data_user_question, topK=topK, withWeight=withWeight)

        if withWeight is True:
            for tag in tags:
                print("tag: %s\t\t weight: %f" % (tag[0], tag[1]))
                if tag[0] in key_list:
                    worksheet.write(inc_index, 0, tag[0])
                    worksheet.write(inc_index, 1, row_col_data_user_question)
                    worksheet.write(inc_index, 2, row_col_data_answer)
                    inc_index += 1
                    break
        else:
            print(",".join(tags))

    workbook.save(current_directory + '\\File\\final_data.xls')

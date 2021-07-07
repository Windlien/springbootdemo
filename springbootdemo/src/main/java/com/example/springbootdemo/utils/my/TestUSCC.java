/**
 * 统一社会信用代码JAVA校验
 */
public class TestUSCC {
    public static void main(String[] args) {
        /*
        * 测试所用的统一社会信用代码来自湖北省民政厅官网的公开信息：
http://zwfw.hubei.gov.cn:8503/xcms/shzzwebsite/jumpPage/shzz_xzxkgg.jhtml?currentPage=1&djType=1&flagname=XKGG
        * */
        String[] test = {"931307310813271435", "911307310813271435",
                "91130125MA07WJNA83", "91130125750281817B",
                "911301257502818178",
                "51420000MJH200408C", "52420000MJH233410E",
                "51420000MJH200395N", "51420000MJH2003791",
                "53420000MJH2448303", "52420000MJH233402K"};
        for (int i = 0; i < 10; i++) {
            if (checkUSCC(test[i]))
                System.out.println("验证通过:" + test[i]);
            else
                System.out.println("验证失败:" + test[i]);
        }
    }

    /*
    [函数名]checkUSCC
    [功能]校验18位统一社会信用代码正确性
    [参数]testUSCC:待校验的统一社会信用代码（要求字母已经保持大写）
    [返回值]boolean类型，0(false)表示验证未通过，1(true)表示验证通过
    */
    public static boolean checkUSCC(String testUSCC) {
        if (testUSCC.length() != 18) {
            System.out.println("统一社会信用代码长度错误");
            return false;
        }
        int[] weight = {1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};  //用于存放权值
        int index = 0;                                                        //用于计算当前判断的统一社会信用代码位数
        char testc;                                                           //用于存放当前位的统一社会信用代码
        int tempSum = 0;                                                      //用于存放代码字符和加权因子乘积之和
        int tempNum = 0;
        for (index = 0; index <= 16; index++) {
            testc = testUSCC.charAt(index);
            if (index == 0) {
                if (testc != '1' && testc != '5' && testc != '9' && testc != 'Y') {
                    System.out.println("统一社会信用代码中登记管理部门代码错误");
                    return false;
                }
            }

            if (index == 1) {
                if (testc != '1' && testc != '2' && testc != '3' && testc != '9') {
                    System.out.println("统一社会信用代码中机构类别代码错误");
                    return false;
                }
            }

            tempNum = charToNum(testc);
            if (tempNum != -1)                                //验证代码中是否有错误字符
            {
                tempSum += weight[index] * tempNum;
            } else {
                System.out.println("统一社会信用代码中出现错误字符");
                return false;
            }
        }
        tempNum = 31 - tempSum % 31;
        if (tempNum == 31) tempNum = 0;
        //按照GB/T 17710标准对统一社会信用代码前17位计算校验码，并与第18位校验位进行比对
        return charToNum(testUSCC.charAt(17)) == tempNum;
    }

    /*
    [函数名]charToNum
    [功能]按照GB32100-2015标准代码字符集将用于检验的字符变为相应数字
    [参数]c:待转换的字符
    [返回值]转换完成后对应的数字,若无法找到字符集中字符，返回-1
    */
    public static int charToNum(char c) {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'A':
                return 10;
            case 'B':
                return 11;
            case 'C':
                return 12;
            case 'D':
                return 13;
            case 'E':
                return 14;
            case 'F':
                return 15;
            case 'G':
                return 16;
            case 'H':
                return 17;
            case 'J':
                return 18;
            case 'K':
                return 19;
            case 'L':
                return 20;
            case 'M':
                return 21;
            case 'N':
                return 22;
            case 'P':
                return 23;
            case 'Q':
                return 24;
            case 'R':
                return 25;
            case 'T':
                return 26;
            case 'U':
                return 27;
            case 'W':
                return 28;
            case 'X':
                return 29;
            case 'Y':
                return 30;
            default:
                return -1;
        }
    }
/*
* 统一代码的具体赋码规则如下：
第一部分（第1位）：登记管理部门代码。暂按国务院序列规则，5表示民政部门。
第二部分（第2位）：机构类别代码。“1”表示社会团体、“2”表示民办非企业单位、“3”表示基金会、“9”表示其他。
第三部分（第3-8位）：登记管理机关行政区划码，参照GB/T 2260中华人民共和国行政区划代码标准。（登记机关所在地的行政区划）。
第四部分（第9-17位）：主体标识码（组织机构代码），其中第17位为主体标识码（组织机构代码）的校验码。第17位校验码算法规则按照《全国组织机构代码编制规则》（国标GB11714—1997）计算。
第五部分（第18位）：统一社会信用代码的校验码。第18位校验码算法规则按照《GB -2015 法人及其他组织统一社会信用代码编制规则》计算。
*
规范性引用文件
    本规范制定主要引用了以下标准或文件。如无特殊说明，凡是注日期的引用文件，其随
    后所有的更正单（不包括勘误的内容）或修订版均不适用于本标准；凡是不注日期的引用文
    件，其最新版本适用于本标准。
    GB/T 1.1-2009 标准化工作导则 第 1 部分：标准的结构和编写规则
    GB/T 2260-2007 中华人民共和国行政区划代码
    GB/T 2659-2000 世界各国和地区名称代码
    GB/T 4754-2017 国民经济行业分类
    GB 11643-1999 公民身份号码
    GB 11714-1997 全国组织机构代码编制规则
    GB/T 12406-2008 表示货币和资金的代码
    GB 32100-2015 法人和其他组织统一社会信用代码编码规则
    其中，行政区划代码最新版本以征信系统使用的为准，具体的可登陆征信系统网站查询、下载。
* */
}
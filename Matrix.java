import java.sql.SQLOutput;
import java.util.Scanner;

public class Matrix {
    public int rowsCount;
    public int colsCount;
    public double[][] array;
    public static Scanner scanner = new Scanner(System.in);
    public void interMatrix() {

        System.out.println("Введите размеры матрицы.");
        System.out.print("Количество строк в вашей матрице:");
        this.rowsCount = scanner.nextInt();
        System.out.print("Количество столбцов в вашей матрице:");
        this.colsCount = scanner.nextInt();
        array = new double[this.rowsCount][this.colsCount];
        System.out.println("Как вы желаете ввести элементы матрицы: 1)поэлементно, 2)построчно?");
        String answerInter = scanner.next();
        if (answerInter.equalsIgnoreCase("1") | answerInter.equalsIgnoreCase("поэлементно")) {
            for (int i = 0; i < this.rowsCount; i++) {
                for (int j = 0; j < this.colsCount; j++) {
                    System.out.print("Введите элемент, который будет находиться на позиции (" + (i + 1) + ";" + (j + 1) + "): ");
                    array[i][j] = scanner.nextDouble();
                }
            }
        } else if (answerInter.equalsIgnoreCase("2") | answerInter.equalsIgnoreCase("построчно")) {
            for (int i = 0; i < this.rowsCount; i++) {
                System.out.printf("Введите элементы для %d строки через пробел: ", i + 1);
                for (int j = 0; j < this.colsCount; j++) {
                    array[i][j] = scanner.nextDouble();
                }
            }
        }
    }

    public void outputMatrix() {
        if (this.rowsCount * this.colsCount != 0) {
            System.out.println("Матрица (" + this.rowsCount + " * " + this.colsCount + "):");
            int max = 0;
            for (int i = 0; i < this.rowsCount; i++) {
                for (int j = 0; j < this.colsCount; j++) {
                    String str = ((Double) array[i][j]).toString();
                    if (str.length() > max) {
                        max = str.length();
                    }
                }
            }
            for (int i = 0; i < this.rowsCount; i++) {
                for (int j = 0; j < this.colsCount; j++) {
                    System.out.format("%" + max + "s ", array[i][j]);
                }
                System.out.println();
            }
        } else {
            System.out.println("Матрица еще не определена");
        }
    }

    //умножение матриц
    public Matrix multiplication(Matrix matrix) {
        //прямое умножение, если оно возможно
        Matrix mult_matrix = new Matrix();
        if (this.colsCount == matrix.rowsCount) {
            mult_matrix.rowsCount = this.rowsCount;
            mult_matrix.colsCount = matrix.colsCount;
            mult_matrix.array = new double[mult_matrix.rowsCount][mult_matrix.colsCount];
            for (int i = 0; i < this.rowsCount; i++) {
                for (int j = 0; j < matrix.colsCount; j++) {
                    for (int l = 0; l < matrix.rowsCount; l++) {
                        mult_matrix.array[i][j] += this.array[i][l] * matrix.array[l][j];
                    }
                }
            }
            return mult_matrix;

            //обратное умножение
        } else if (this.rowsCount == matrix.colsCount) {
            System.out.print("Прямое умножение невозможно, но возможно умножить матрицы в обратном порядке. Сделать это?");
            String answer = scanner.next();
            if (answer.equalsIgnoreCase("да") | answer.equalsIgnoreCase("д") | answer.equalsIgnoreCase("yes") | answer.equalsIgnoreCase("y")) {
                mult_matrix.rowsCount = matrix.rowsCount;
                mult_matrix.colsCount = this.colsCount;
                mult_matrix.array = new double[mult_matrix.rowsCount][mult_matrix.colsCount];
                for (int i = 0; i < matrix.rowsCount; i++) {
                    for (int j = 0; j < this.colsCount; j++) {
                        for (int l = 0; l < this.rowsCount; l++) {
                            mult_matrix.array[i][j] += matrix.array[i][l] * this.array[l][j];
                        }
                    }
                }
                return mult_matrix;

            } else {
                return null;
            }

            //если ни прямой, ни обратное умножения невозможны
        } else {
            System.out.println("Умножение матриц невозможно!");
            return null;
        }
    }

    //складывание матриц
    public Matrix add(Matrix matrix) {
        if (this.colsCount == matrix.colsCount && this.rowsCount == matrix.rowsCount) {

            //создаем объект класса Matrix, в котором будет наша матрица после складывания
            Matrix sum_matrix = new Matrix();
            sum_matrix.rowsCount = this.rowsCount;
            sum_matrix.colsCount = this.colsCount;
            sum_matrix.array = new double[this.rowsCount][this.colsCount];

            //сам процесс складывания
            for (int i = 0; i < this.rowsCount; i++) {
                for (int j = 0; j < this.colsCount; j++) {
                    sum_matrix.array[i][j] = this.array[i][j] + matrix.array[i][j];
                }
            }
            return sum_matrix;
        }

        //срабатывает, если сложение нельзя осуществить
        System.out.println("Сложение невозможно.");
        return null;
    }

    //поиск ранга матрицы
    public static int searchrank(Matrix matrix) {

        //пользуемся методом gauss, который приводит нашу матрицы к ступенчатому виду
        gauss(matrix);
        int result = 0;
        int temp;

        //смотрим, сколько строчек в нашей треугольной матрице состоят полностью из нулей
        for (int i = 0; i < matrix.rowsCount; i++) {
            temp = 0;
            for (int j = 0; j < matrix.colsCount; j++) {
                if (matrix.array[i][j] == 0) {
                    temp++;
                }
            }
            if (temp == matrix.colsCount) {
                result++;
            }
        }
        return matrix.rowsCount - result;
    }

    //осуществление прямого хода гаусса
    public static Matrix gauss(Matrix matrix) {

        for (int i = 0; i < Math.min(matrix.rowsCount, matrix.colsCount); i++) {


            double maxNumber = Math.abs(matrix.array[i][i]);
            int maxRow = i;
            for (int j = i + 1; j < matrix.rowsCount; j++) {
                if (Math.abs(matrix.array[j][i]) > maxNumber) {
                    maxNumber = Math.abs(matrix.array[j][i]);
                    maxRow = j;
                }
            }

            double[] temp_array = matrix.array[i];
            matrix.array[i] = matrix.array[maxRow];
            matrix.array[maxRow] = temp_array;

            for (int k = i + 1; k < matrix.rowsCount; k++) {
                double coef = -(matrix.array[k][i] / matrix.array[i][i]);

                for (int j = i; j < matrix.colsCount; j++) {
                    if (i == j) {
                        matrix.array[k][j] = 0;
                    } else {
                        matrix.array[k][j] += coef * matrix.array[i][j];
                    }
                }
            }
        }
        return matrix;
    }

    public Matrix solve(Matrix matrix) {

        if (matrix.colsCount != 1 | this.rowsCount != matrix.rowsCount) {
            return null;
        }

        //Построение расширенной матрицы
        Matrix gauss_matrix = new Matrix();
        gauss_matrix.rowsCount = this.rowsCount;
        gauss_matrix.colsCount = this.colsCount + 1;
        gauss_matrix.array = new double[gauss_matrix.rowsCount][gauss_matrix.colsCount];

        for (int i = 0; i < this.rowsCount; i++) {
            for (int j = 0; j < gauss_matrix.colsCount; j++) {
                if (j == gauss_matrix.colsCount - 1) {
                    gauss_matrix.array[i][j] = matrix.array[i][0];
                } else {
                    gauss_matrix.array[i][j] = this.array[i][j];
                }
            }
        }

        gauss_matrix.array = gauss(gauss_matrix).array;

        //Если матрица имеет единственное решение
        if (searchrank(this) == searchrank(gauss_matrix) && searchrank(this) == this.colsCount) {

            //Обратный ход метода Гаусса
            for (int i = gauss_matrix.rowsCount - 1; i >= 0; i--) {
                for (int k = i - 1; k >= 0; k--) {
                    double coef = -(gauss_matrix.array[k][i] / gauss_matrix.array[i][i]);

                    for (int j = gauss_matrix.colsCount - 1; j >= 0; j--) {
                        if (i == j) {
                            gauss_matrix.array[k][j] = 0;
                        } else {
                            gauss_matrix.array[k][j] += coef * gauss_matrix.array[i][j];
                        }
                    }
                }
            }

            System.out.println("Данная система имеет одно решение.");

            //m_answer — матрица ответов
            Matrix m_answer = new Matrix();
            m_answer.rowsCount = gauss_matrix.rowsCount;
            m_answer.colsCount = 1;
            m_answer.array = new double[m_answer.rowsCount][m_answer.colsCount];
            for (int i = 0; i < m_answer.rowsCount; i++) {
                m_answer.array[i][0] = gauss_matrix.array[i][gauss_matrix.colsCount - 1] / gauss_matrix.array[i][i];
                m_answer.array[i][0] = Math.round(m_answer.array[i][0] * 1_000_000) / 1_000_000d;
            }
            return m_answer;

            //Если система не имеет решений
        } else if (searchrank(this) != searchrank(gauss_matrix)) {
            System.out.println("Данная система не имеет решений.");
            return null;

            //Если система имеет бесконечное количество решений
        } else {
            System.out.println("Данная система имеет бесконечное количество решений.");
            return null;
        }
    }

    public static double det(Matrix matrix) {

        double count = 1;
        for (int i = 0; i < Math.min(matrix.rowsCount, matrix.colsCount); i++) {


            double maxNumber = Math.abs(matrix.array[i][i]);
            int maxRow = i;
            for (int j = i + 1; j < matrix.rowsCount; j++) {
                if (Math.abs(matrix.array[j][i]) > maxNumber) {
                    maxNumber = Math.abs(matrix.array[j][i]);
                    maxRow = j;
                }
            }
            if (maxRow != i) {
                count *= -1;
            }

            double[] temp_array = matrix.array[i];
            matrix.array[i] = matrix.array[maxRow];
            matrix.array[maxRow] = temp_array;

            for (int k = i + 1; k < matrix.rowsCount; k++) {
                double coef = -(matrix.array[k][i] / matrix.array[i][i]);

                for (int j = i; j < matrix.colsCount; j++) {
                    if (i == j) {
                        matrix.array[k][j] = 0;
                    } else {
                        matrix.array[k][j] += coef * matrix.array[i][j];
                    }
                }
            }
        }
        double deg = 1;
        for (int i = 0; i < matrix.rowsCount; i++){
            deg *= matrix.array[i][i];
        }
        return (deg*count);
    }

    public Matrix kramer(Matrix matrix) {

        if (this.rowsCount == this.colsCount && matrix.colsCount == 1 && matrix.rowsCount == this.rowsCount) {

            //матрица для записи ответов
            Matrix answer_kramer = new Matrix();
            answer_kramer.rowsCount = matrix.rowsCount;
            answer_kramer.colsCount = 1;
            answer_kramer.array = new double[answer_kramer.rowsCount][answer_kramer.colsCount];

            //основная матрица
            Matrix matrix_delta = new Matrix();
            matrix_delta.colsCount = this.colsCount;
            matrix_delta.rowsCount = this.rowsCount;
            matrix_delta.array = new double[matrix_delta.rowsCount][matrix_delta.colsCount];
            for (int i = 0; i < matrix_delta.rowsCount; i++) {
                for (int j = 0; j < matrix_delta.colsCount; j++) {
                    matrix_delta.array[i][j] = this.array[i][j];
                }
            }

            double delta = det(matrix_delta); //сюда запишем определитель основной матрицы

            if(delta == 0){
                System.out.println("Данную систему нельзя решить с помощью метода Крамера!");
                return null;
            }

            Matrix matrix_delta123 = new Matrix();
            matrix_delta123.colsCount = this.colsCount;
            matrix_delta123.rowsCount = this.rowsCount;
            matrix_delta123.array = new double[matrix_delta123.rowsCount][matrix_delta123.colsCount];

            for (int c = 0; c < this.colsCount; c++) {

                //заполнение
                for (int i = 0; i < matrix_delta123.rowsCount; i++) {
                    for (int j = 0; j < matrix_delta123.colsCount; j++) {
                        matrix_delta123.array[i][j] = this.array[i][j];
                        if (j == c) {
                            matrix_delta123.array[i][j] = matrix.array[i][0];
                        }
                    }
                }

                double delta123 = det(matrix_delta123);

                //находим ответы
                answer_kramer.array[c][0] = Math.round((delta123/delta) * 1_000_000) / 1_000_000d;
            }

            return answer_kramer;

        } else {
            System.out.println("Извините, эту систему нельзя решить с помощью метода Крамера!");
            return null;
        }
    }

    public String toString() {
        String s = "Матрица (" + this.rowsCount + " * " + this.colsCount + "): ";
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < colsCount; j++) {
                s += " " + array[i][j];
            }
        }
        return s;
    }
}


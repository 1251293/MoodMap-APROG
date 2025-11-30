# Mood Tracker Analysis - Trabalho Prático APROG 2025-2026

## Identificação do Grupo

**Turma:** 1DG
**Aluno 1:** Nuno Lopes - 1251327
**Aluno 2:** Matilde Santos - 1251293

## Descrição do Projeto

Sistema de análise de humor desenvolvido para a empresa GoodMood, que permite monitorizar e analisar os níveis de humor diários dos funcionários. O sistema identifica padrões de baixo humor (low mood), deteta transtornos emocionais e recomenda terapias adequadas.

## Funcionalidades Implementadas

### a) Leitura do MoodMap

* Leitura de dados via teclado ou ficheiro de texto
* Validação completa dos dados de entrada
* Verificação de valores fora do intervalo [1-5]
* Verificação de dimensões inválidas (pessoas e dias < 1)
* Armazenamento em matriz bidimensional

**Responsável:** Nuno Lopes (Aluno 1)

### b) Visualização do MoodMap

* Formatação em tabela com cabeçalhos
* Apresentação clara de pessoas e dias
* Separadores visuais para facilitar leitura

**Responsável:** Matilde Santos (Aluno 2)

### c) Média do Humor por Dia

* Cálculo da média de humor para cada dia
* Apresentação formatada com uma casa decimal
* Identificação de tendências diárias

**Responsável:** Nuno Lopes (Aluno 1)

### d) Média do Humor por Pessoa

* Cálculo da média individual
* Apresentação formatada com uma casa decimal
* Identificação de padrões individuais

**Responsável:** Matilde Santos (Aluno 2)

### e) Dias com Maior Nível de Humor

* Identificação do(s) dia(s) com média máxima
* Suporte para múltiplos dias com o mesmo valor máximo
* Apresentação do valor da média máxima

**Responsável:** Nuno Lopes (Aluno 1)

### f) Percentagem dos Níveis de Humor

* Cálculo da distribuição percentual de cada nível (1-5)
* Apresentação ordenada de forma decrescente
* Formatação com uma casa decimal

**Responsável:** Matilde Santos (Aluno 2)

### g) Pessoas com Transtorno Emocional

* Deteção de pessoas com dias consecutivos de low mood (valores < 3)
* Cálculo do máximo de dias consecutivos
* Apresentação apenas de pessoas com 2 ou mais dias consecutivos
* Mensagem "Nenhum" quando não há casos detetados

**Responsável:** Nuno Lopes (Aluno 1)

### h) Gráficos de Humor por Pessoa

* Visualização gráfica individual do humor
* Escala vertical adaptativa (mostra apenas intervalo usado)
* Legenda horizontal em múltiplos de 5 dias
* Representação clara com asteriscos

**Responsável:** Matilde Santos (Aluno 2)

### i) Recomendação de Terapia

* Identificação de pessoas que necessitam de terapia
* Critérios:

    * 2-4 dias consecutivos de low mood: "listen to music"
    * 5+ dias consecutivos de low mood: "psychological support"
* Mensagem "Nenhum" quando não há recomendações

**Responsável:** Nuno Lopes (Aluno 1)

### j) Pessoas com Humor Semelhante

* Identificação do par de pessoas com mais dias de humor igual
* Em caso de empate, seleção do par com menor identificação
* Mensagem "Nenhum" quando não há igualdades

**Responsável:** Matilde Santos (Aluno 2)

## Estrutura do Código

### Métodos Principais

#### Gestão do Programa

* `main()` - Ponto de entrada, gestão do menu
* `executarPrograma(Scanner)` - Execução de todas as funcionalidades
* `lerDadosFicheiro(String)` - Leitura de ficheiro com tratamento de erros

#### Validação de Dados

* `obterDado(int, int, Scanner)` - Leitura de inteiro com validação de intervalo
* `obterNumeroDePessoasOuDiasValidado(Scanner)` - Leitura de dimensões com validação (especialmente para ficheiros)
* `fazerMoodMapValidado(int, int, Scanner, int, int)` - Leitura da matriz de humor com validação completa

#### Cálculos e Análise

* `calcMedia(int, int, int[][])` - Cálculo de médias (usado para dias e pessoas)
* `contarOcorrenciasMood(int[][], int, int)` - Contagem de ocorrências por nível
* `diasMauHumorConsectutivos(int[][], int, int, int)` - Deteção de transtornos
* `diasMoodMaximo(float[], int)` - Identificação de dias com maior média
* `pessoasMaisSemelhantes(int[][], int, int)` - Cálculo de similaridade

#### Visualização

* `printMoodMap(int[][])` - Impressão da matriz formatada
* `printLinhaDay(int)` - Cabeçalho de dias
* `printSeparator(int)` - Linha separadora
* `printLinhaPessoa(int, int[], int)` - Linha de dados de pessoa
* `printAvgMood(float[], int)` - Apresentação de médias por dia
* `printMediaPerPerson(float[])` - Apresentação de médias por pessoa
* `printPercentagemMoodLevels(int[][], int, int)` - Apresentação de percentagens
* `fazerGrafico(int, int[][], int)` - Geração de gráficos
* `fazerMatrizGrafico(int[], int, int, int)` - Construção da matriz do gráfico
* `recommendedTherapy(int[][], int, int)` - Recomendações de terapia

## Decisões de Implementação

### Validação de Dados

Implementação de validação em duas camadas:

* Validação interativa para entrada por teclado (permite correção)
* Validação com retorno de erro para ficheiros (retorna -1 ou null)

### Tratamento de Erros

Sistema de validação:

* Métodos retornam valores sentinela (-1 para int, null para arrays)
* Mensagens de erro claras e informativas
* Interrupção segura da execução em caso de erro

### Cálculo de Médias

Método único `calcMedia()` usado tanto para médias por dia como por pessoa, através da manipulação inteligente dos índices da matriz.

### Deteção de Transtornos Emocionais

Implementação de algoritmo que identifica o máximo de dias consecutivos com low mood (valores < 3) para cada pessoa, garantindo que apenas pessoas com 2 ou mais dias consecutivos são reportadas.

### Gráficos Adaptativos

Os gráficos ajustam automaticamente a escala vertical para mostrar apenas o intervalo de valores presente nos dados, eliminando linhas vazias desnecessárias.

## Formato de Ficheiro de Entrada

```
<Descrição do MoodMap>
<Número de Pessoas> <Número de Dias>
<Humor Pessoa 0 - Dia 0> <Humor Pessoa 0 - Dia 1> ... <Humor Pessoa 0 - Dia N>
<Humor Pessoa 1 - Dia 0> <Humor Pessoa 1 - Dia 1> ... <Humor Pessoa 1 - Dia N>
...
```

### Exemplo:

```
Sales dept. - november
3 15
4 2 3 3 5 4 1 4 2 4 3 3 4 4 2
4 5 3 4 3 5 5 5 4 1 1 1 1 4 5
1 1 1 1 1 1 2 1 1 1 2 2 1 1 2
```

## Validações Implementadas

### Validação de Dimensões

* Número de pessoas deve ser maior que 0
* Número de dias deve ser maior que 0
* Mensagens de erro específicas para cada caso

### Validação de Valores de Humor

* Valores devem estar no intervalo [1, 5]
* Identificação precisa da posição do erro (pessoa e dia)
* Mensagem indica valor inválido e posição

### Validação de Ficheiros

* Tratamento de formato inválido dos dados lidos

## Testes Realizados

### test_small.txt

* 2 pessoas, 5 dias
* Teste básico de funcionalidade
* Validação de caso com low mood prolongado

### test_medium.txt

* 5 pessoas, 10 dias
* Padrões variados de humor
* Múltiplos casos de transtorno emocional

### test_large.txt

* 8 pessoas, 20 dias
* Teste de escala e performance
* Diversos padrões complexos

### teste.txt

* Exemplo exato do enunciado
* Validação de conformidade com especificação

### Menu Interativo:

```
Mood Tracker Analysis
---------------------
Read data from Keyboard (1)
Read data from File (2)
```

## Constantes Utilizadas

* `LOW_MOOD = 3` - Limiar para low mood (valores < 3)
* `LIMITE_INF_ESCALA = 1` - Valor mínimo da escala de humor
* `LIMITE_SUP_ESCALA = 5` - Valor máximo da escala de humor

## Critérios de Terapia

| Dias Consecutivos em Low Mood | Terapia Recomendada   |
| ----------------------------- | --------------------- |
| 0-1                           | Sem necessidade       |
| 2-4                           | Listen to music       |
| 5+                            | Psychological support |

## Notas Técnicas

### Modularização

O código está organizado em módulos funcionais claros, cada um responsável por uma tarefa específica. Comentários descritivos foram adicionados para facilitar a compreensão.

### Estruturas de Dados

* Matriz bidimensional `int[][]` para armazenamento do MoodMap
* Arrays unidimensionais `float[]` para médias
* Arrays `String[][]` para construção de gráficos

### Formatação de Output

Toda a saída segue o formato especificado no enunciado, com alinhamento correto e formatação numérica adequada (uma casa decimal para percentagens e médias).

## Observações Finais

O programa implementa todas as funcionalidades solicitadas no enunciado, com validação robusta de dados e tratamento adequado de erros. A distribuição de tarefas entre os elementos do grupo foi equilibrada, respeitando a sugestão do enunciado com pequenos ajustes para melhor organização do código.

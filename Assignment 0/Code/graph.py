import matplotlib.pyplot as plt

widths, probabilities, duration = [], [], []

with open('output.txt') as reader:


	# Separating the probiblity, width and duration values from each line
	outputFile = reader.read()
	outputFile = outputFile.replace(' ', '')
	outputFile = outputFile.replace('\n', ',')
	outputFile = outputFile.split(',')

	i = 0

	while (i < (len(outputFile) - 3)):

		# Storing the respective values of widths, probabilities and duration into the corresponding arrays
		widths.append(int(outputFile[i]))
		probabilities.append(float(outputFile[i+1]))
		duration.append(float(outputFile[i+2]))
		i += 3

# Plot the graphs
fig = plt.figure()
graph = fig.add_subplot(111, projection='3d', xlabel='Probability', ylabel='Width', zlabel='Time (s)')
graph.plot(xs=probabilities, ys=widths, zs=duration, color="red")
plt.savefig('graph.png')

plt.subplot(1,2,1)
plt.xlabel('Probability')
plt.ylabel('Duration')
plt.plot(probabilities, duration, color="blue")

plt.subplot(1,2,2)
plt.xlabel('Width')
plt.ylabel('Duration')
plt.plot(widths, duration, color="green")

plt.tight_layout()

plt.savefig('graph_pw.png')
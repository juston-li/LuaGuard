-- The authors of this work have released all rights to it and placed it
-- in the public domain under the Creative Commons CC0 1.0 waiver
-- (http://creativecommons.org/publicdomain/zero/1.0/).
-- 
-- THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
-- EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
-- MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
-- IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
-- CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
-- TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
-- SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
-- 
-- Retrieved from: http://en.literateprograms.org/Fibonacci_numbers_(Lua)?oldid=19124
 
function fib(n) return n<2 and n or fib(n-1)+fib(n-2) end

function fastfib(n)
	fibs={1,1}

	for i=3,n do
		fibs[i]=fibs[i-1]+fibs[i-2]
	end

	return fibs[n]
end

for n=1,30 do print(fib(n)) end
for n=1,30 do print(fastfib(n)) end
 

TITLE heap
; Author:
; Program Name:
; Program Description:
; Date

INCLUDE Irvine32.inc

.data
    heap dd 10000 dup(?)
    sz dd 0
    mxsz dd 10000
    fullheap byte "The heap is full, fail to insert"
    emptyheap byte "The heap is empty, fail to remove"
    inputcount byte "Please, enter input count: "
    inputvalue byte "Please, enter an integer: "
.code
main PROC

mov edx, offset inputcount
call writestring
call readint

mov ecx, eax
mov esi, eax
mov edx, offset inputvalue
input_loop:
call writestring
call readint
call insert
loop input_loop

call crlf

mov ecx, esi
output_loop:
call remove
call writeint
call crlf
loop output_loop

  exit
main ENDP

top proc
cmp sz, 0
jz return_zero
mov EAX, [heap]
mov ebx, 0
ret
return_zero:
mov eax, -1
mov ebx, 1
ret
top endp

get_right_child proc
add eax, eax
add eax, 2
ret
get_right_child endp

get_left_child proc
add eax, eax
add eax, 1
ret
get_left_child endp

parent proc
sub eax, 1
sar eax, 1
ret
parent endp

heap_up proc uses eax ebx edx esi
mov ebx, eax          ;move current node to ebx
call parent           ;get parent index in eax
mov esi, heap[ebx*4]  ;load child value
uwhile:               ;loop while current isn't root or it's parent is greater
cmp ebx, 0            ;index > 0 else return
jle enduwhile   
mov edx, heap[eax*4]  ;load parent value in edx
cmp edx, esi  ;parent value < current node else return
jge enduwhile
mov heap[ebx*4], edx  ;exchange current node with parent
mov ebx, eax          ;index for current node = parent index
call parent           ;get new parent index
jmp uwhile            ;loop
enduwhile: mov heap[ebx*4], esi  ;current node = child
ret
heap_up endp

heap_down proc uses eax ebx ecx edx esi edi ebp
mov esi, heap[eax*4]  ;load child value
mov edi, sz           ;get size of heap array
sar edi,1             ;get size/2
uwhile:               ;loop while current isn't root or it's parent is greater
cmp eax, edi          ;index < size/2 else return
jge enduwhile   
push eax              ;store parent index
call get_left_child   ;left child index in eax
mov ebx, eax          ;left child index in ebx
pop eax               ;return parent index from stack
push eax              ;store parent index
call get_right_child  ;right child index in eax
mov ecx, eax          ;right child index in ecx
pop eax               ;return parent index from stack
cmp ecx, sz           ;if right child greater than size left is larger child
jge uelse              
mov ebp, heap[ebx*4]  ;if left is less than right child right child is larger
cmp ebp, heap[ecx*4]
jge uelse
mov ebx, ecx          ;set left as larger
uelse:
cmp esi, heap[ebx*4]  ;break if larger child greater than parent
jge enduwhile
mov edx,heap[ebx*4]   ;swap largerchild with parent
mov heap[eax*4], edx  ;exchange current node with parent
mov eax, ebx          ;index for current node = parent index
jmp uwhile            ;loop
enduwhile: 
mov heap[eax*4], esi  ;current node = child
ret
heap_down endp

insert proc uses ebx edx
mov ebx, sz
cmp ebx, mxsz
je failinsert
mov heap[ebx*4], eax
mov eax, sz
inc dword ptr[sz]
call heap_up
ret

failinsert:
mov edx, offset fullheap
call writestring
call crlf
ret
insert endp

remove proc uses ebx ebp edx

mov ebx, sz
cmp ebx, 0
je failremove

mov ebp, heap
dec dword ptr[sz]
mov ebx, sz
mov ebx, heap[ebx*4]
mov heap, ebx
mov eax, 0
call heap_down
mov eax, ebp

ret

failremove:
mov edx, offset emptyheap
call writestring
call crlf
mov eax, 0
ret
remove endp

END main
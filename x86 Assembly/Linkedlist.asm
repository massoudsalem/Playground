; Heap                        (Heap.asm)

INCLUDE Irvine32.inc

Node STRUCT     
;intalize node content (value,next pointer to next node)
content dd ?  ;value
next dd NULL  ;pointer
Node ends

.data
NODE_SIZE = 8   ;size of node
List_SIZE DD 0  ;linkedlist size
first dword NULL ;refer to first node
last dword NULL  ;refer to last node
hHeap   DWORD ?				; handle to the process heap
new_node_ptr  DWORD ?		; pointer to block of memory allocate 
str1 BYTE "The list size is: ",0
item_not_found byte "item not founded.",0
empty byte "The list is empty.",0
failmsg byte "Fail to allocate new node.",0
arrow byte "->",0

.code
main PROC
	call create_heap
	mov eax, 17			;node data
	call insert_last
	mov eax, 15			;node data
	call insert_first
	mov eax, 14			;node data
	call insert_first
	mov eax, 16
	call insert_last
	mov eax, 18
	call insert_last
	
	call display_list
	call delete_first
	call display_list
	call delete_last
	call display_list
	call delete_last
	call display_list
	call delete_first
	call display_list
	call delete_last
	call display_list
	call delete_last
	mov eax,20
	call insert_first
	call display_list
	call delete_first
	mov edx,15
	mov eax,40
	call insert_after
	mov eax,50
	call insert_first
	call display_list
	mov eax,70
	call insert_last
	call display_list
	mov eax,100
	call delete 
	call display_list
	call show_size
	call delete_first
	call display_list
	call show_size
	call delete_first
	call display_list
	call show_size
	call delete_first
	call display_list
	call show_size
	mov eax,1
	call insert_first
	mov eax,100
	call insert_last
	call display_list
	call show_size
	call delete_last
	call display_list
	call show_size
	call delete_last
	call display_list
	call show_size
	call delete_last
	call display_list
quit::
	exit
main ENDP

show_size proc
;--------------------------------------------------------
; show list size
; Receives: nothing
; Returns: nothing
;--------------------------------------------------------
    mov edx,offset str1
    call writestring
	mov eax,list_SIZE
	call WriteDec
	call crlf
	ret
show_size endp

delete proc uses esi edx ecx edi ebx
;--------------------------------------------------------
; Delete a specific element in the list
; Receives: an element to delete in eax
; Returns: nothing 
;--------------------------------------------------------
    mov ecx, List_SIZE   ;loop size
	jcxz go              ;loop is empty?
	mov esi, first		 ; point to the first
	
L1:		
	mov edi,(Node ptr [esi]).content  ;edi=node value
	cmp eax,edi                       ;if node value == item to delete
	je del
	mov ebx,esi                       ;store previous node
	mov esi, (Node ptr [esi]).next    ;move to new node
    loop L1                           
	jcxz go                           ;loop finish without found item
 
del:                                  ;item is founded
	mov edx,ecx                       ;if item is last one
	mov edi,1                         
	cmp edx,edi                   
	je delast
	mov edi,list_SIZE                 ;if item is first one
	cmp edx,edi
	je delfirst
	mov edx,(Node ptr [esi]).next      ;delete item 
	mov (Node ptr [ebx]).next,edx
    INVOKE HeapFree, hHeap, 0, esi
    dec Dword ptr List_SIZE           ;decreament size
	ret
delfirst:                             ;item is first one
	call delete_first
	ret 
delast:                               ;item is last one
	call delete_last
	ret 
go:                                  ;item not founded or empty list
	mov edx,offset item_not_found
	call writestring
	call crlf
ret
delete endp


delete_last proc 
;--------------------------------------------------------
; Delete the last element in the list
; Receives: nothing
; Returns: the deleted last element of the list 
;--------------------------------------------------------
     mov ecx, List_SIZE                 ;loop size
	 jcxz go
	 mov esi, last                      ;eax = value of deleted item
	 mov eax, (Node ptr [esi]).content
     INVOKE HeapFree, hHeap, 0, last    ;free node
	 dec Dword ptr List_SIZE			;decrease the list size
	 mov ecx, List_SIZE					;if it was the only item in list 
	 jcxz empt
	
	mov esi, first						; point to the first
L1:	mov	eax,esi							; get a node
	mov esi, (Node ptr [esi]).next		;move to next node
	loop L1                          
	mov last,eax						;find item before last ,last = item before last deleted
	ret
	go:
	mov edx,offset empty
	call writestring
	call crlf
	ret
	empt:
	mov last,NULL
	mov first,NULL
	ret
delete_last endp
insert_after proc uses ecx esi ebx edi edx
;--------------------------------------------------------
; inserts after a specific element in the list
; Receives: element to insert after in eax and item to be inserted in edx
; Returns: nothing
;--------------------------------------------------------
    mov ecx, List_SIZE      ;loop 
	jcxz go    ;if linkedlist empty

	mov esi, first		    ; point to the first
L1:	mov	ebx,esi	            ;find item to insert after
	mov edi,(Node ptr [esi]).content
	mov esi, (Node ptr [esi]).next
	cmp eax,edi      ;if node value== value to insert after
	je after
	loop L1
	jcxz lastitem     ;loop finished without finf item
	go:
	mov edx,offset item_not_found
	call writestring
	call crlf
	ret
	after:
	mov edi,ecx   ;if item was last one
	mov eax,1
	cmp eax,edi
	je lastitem     
	mov eax,edx
	call create_node       ;insert value in middle

	mov esi, new_node_ptr	
	mov edx,(Node ptr [ebx]).next
	mov (Node ptr [ebx]).next,esi
	mov (Node ptr[esi]).next,edx
	inc Dword ptr List_SIZE
	ret
	lastitem:
	mov eax,edx
	call insert_last
	ret
insert_after endp

delete_first proc uses esi edi edx ecx
;--------------------------------------------------------
; Delete the first element in the list
; Receives: nothing
; Returns: the deleted first element of the list 
;--------------------------------------------------------
	mov ecx, List_SIZE
	jcxz go
	mov esi, first
	mov eax, (Node ptr [esi]).content
	mov edi, (Node ptr [esi]).next
	cmp edi, NULL
	jne cont
	mov last, NULL
	cont:
	INVOKE HeapFree, hHeap, 0, first
	mov first, edi
	dec Dword ptr List_SIZE			;increase the list size
	ret
	go:
	mov edx,offset empty
	call writestring
	call crlf
	ret
delete_first endp

insert_last proc uses esi edx
;--------------------------------------------------------
; Insert at the end ot the list
; Receives: element to be inserted
; Returns: nothing
;--------------------------------------------------------
	call create_node
	cmp List_SIZE, 0				;if (listSize == 0)
	jne cont
	call insert_first
	ret 
	jmp jmpelse
	cont:
	mov esi, last					;last.next = newNode
	mov edx, new_node_ptr			;
	mov (Node ptr [esi]).next, edx	;
	jmpelse:
	mov esi, new_node_ptr			;last = newNode
	mov last, esi					;
	inc Dword ptr List_SIZE			;increase the list size
	ret
insert_last endp

insert_first proc uses esi edx
;--------------------------------------------------------
; Insert at the beginning ot the list
; Receives: element to be inserted
; Returns: nothing
;--------------------------------------------------------
	call create_node				;create new node new Node(eax, edi)
	cmp List_SIZE, 0				;if (listSize == 0)
	jne cont						
	mov esi, new_node_ptr			;last = newNode
	mov last, esi					;
	cont:
	mov esi, new_node_ptr			;newNode.next = first
	mov edx, first					;
	mov (Node ptr [esi]).next, edx	;
	mov first, esi					;first = newNode
	inc Dword ptr List_SIZE			;increase the list size
	ret
insert_first endp

create_node proc uses edx esi
	call allocate_new_node
	jnc	 allocation_success		; failed (CF = 1)?
	call WriteWindowsMsg
	call Crlf
	jmp	 fail
allocation_success:				; ok to fill the node
	call fill_node
	jmp sucessreturn
fail:
	mov edx, offset failmsg
	call writestring
sucessreturn:
	ret
create_node endp

create_heap proc
;--------------------------------------------------------
; Request the heap of the process address.
; Receives: nothing
; Returns: the heap address in hHeap in data segment
;--------------------------------------------------------
	INVOKE GetProcessHeap		; get handle to prog's heap
	.IF eax == NULL			; failed?
	call	WriteWindowsMsg
	jmp	quit
	.ELSE
	mov	hHeap,eax		; success
	.ENDIF
create_heap endp


allocate_new_node PROC USES eax
;--------------------------------------------------------
; Dynamically allocates space for the node.
; Receives: nothing
; Returns: CF = 0 if allocation succeeds.
;--------------------------------------------------------
	INVOKE HeapAlloc, hHeap, HEAP_ZERO_MEMORY, NODE_SIZE
	
	.IF eax == NULL
	   stc				; return with CF = 1
	.ELSE
	   mov  new_node_ptr,eax		; save the pointer
	   clc				; return with CF = 0
	.ENDIF

	ret
allocate_new_node ENDP

fill_node PROC USES ecx edx esi edi
;--------------------------------------------------------
; Fills all the node with a single integer.
; Receives: nothing
; Returns: nothing
;--------------------------------------------------------
	mov edi, NULL		;next node
	mov	ecx,NODE_SIZE			; loop counter
	mov	esi,new_node_ptr			; point to the array

	mov (Node ptr [esi]).content, eax
	mov (Node ptr [esi]).next, edi
	ret
fill_node ENDP

display_list PROC USES eax ebx ecx esi edx
;--------------------------------------------------------
; Displays the list
; Receives: nothing
; Returns: nothing
;--------------------------------------------------------
	mov	ecx, List_SIZE	; loop counter
	mov	esi, first		; point to the first
	
	mov edx, offset arrow
	jcxz go
L1:	mov	eax,[esi]		; get a node
	mov	ebx,TYPE Dword
	call WriteDec		; display it
	mov esi, (Node ptr [esi]).next
	call writestring
	loop L1
	call crlf
	go:

	ret
display_list ENDP

END main

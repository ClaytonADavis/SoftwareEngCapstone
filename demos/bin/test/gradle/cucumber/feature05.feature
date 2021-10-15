Feature: Allow named arguments to be mixed with positional arguments in any order.
  
  Rather than placing all named arguments at the end (after all positional
  arguments), the parsing library should allow them to be placed anywhere
  in the argument list, including at the very beginning.
  
  The "WordSearch" demonstration program should be updated to include this new
  functionality. Note that this should NOT break the previous feature, since
  it assumed the named arguments would be at the end, which is still a legal
  position here.


  Scenario Outline: Demonstrate the library functionality by running the demo
    program "WordSearch" that finds a target word in a grid of letters if it exists.
    
    Given the program "<progname>" has started with arguments "<args>"
    When the user views the terminal
    Then the output should be "<output>"

    Examples:
    | progname   | args                                                                      | output                                                 |
    | WordSearch | --width 6 softwaeskqermilvcqputeromocertpuiopprogram --height 7 milk      | m:3,1 i:3,2 l:3,3 k:2,3                                |
    | WordSearch | softwaeskqermilvcqputeromocertpuiopprogram --height 7 software --width 6  | s:1,1 o:1,2 f:1,3 t:1,4 w:1,5 a:1,6 r:2,6 e:2,5        |
    | WordSearch | --height 7 --width 6 softwaeskqermilvcqputeromocertpuiopprogram program   | p:6,1 r:7,1 o:7,2 g:7,3 r:7,4 a:7,5 m:7,6              |
    | WordSearch | softwaeskqermilvcqputeromocertpuiopprogram --width 6 computer --height 7  | c:5,3 o:5,2 m:5,1 p:4,1 u:4,2 t:4,3 e:4,4 r:4,5        |
    | WordSearch | --height 7 softwaeskqermilvcqputeromocertpuiopprogram retro --width 6     | retro not found                                        |
    | WordSearch | soptwerhomnioroeroer --height 4 sophomore                                 | s:1,1 o:1,2 p:1,3 h:2,3 o:2,4 m:2,5 o:3,5 r:4,5 e:4,4  |
    | WordSearch | --height 4 soptwerhomnioroeroer senior                                    | s:1,1 e:2,1 n:3,1 i:3,2 o:3,3 r:3,4                    |
    | WordSearch | soptwerhomnioroeroer --width 4 noroom                                     | n:3,3 o:4,3 r:4,2 o:4,1 o:3,1 m:3,2                    |
    | WordSearch | --width 4 softwaeskqermilvcqputeromocertpuiopprogram --height 5 retro     | WordSearch error: grid dimensions (4 x 5) do not match grid length (42) |
    | WordSearch | soptwerhomnioroeroer --width noroom                                       | WordSearch error: the value noroom is not of type integer   |
    | WordSearch | soptwerhomnioroeroer noroom --width                                       | WordSearch error: no value for width                        |
    | WordSearch | --height soptwerhomnioroeroer noroom --width                              | WordSearch error: the value soptwerhomnioroeroer is not of type integer  |


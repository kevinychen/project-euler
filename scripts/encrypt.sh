# Encrypts and decrypts files.
# 
# A file to encrypt must be of the form:
# 
#    public class p[NUMBER].java {
#        ...
#        check([PASSKEY]);
#        // or check(..., [PASSKEY]);
#        // or check(..., [PASSKEY]L);
#        // or check(..., \"[PASSKEY]\");
#        ...
#    }
#
# During encryption, it is converted to the following, and the
# [NUMBER] -> [PASSKEY] mapping is stored in ANSWERS_FILE:
#
#    [NUMBER]
#    [AES-256-CBC encrypted binary]
#
# During decryption, the first [NUMBER] line is used to fetch the correct
# passkey from ANSWERS_FILE in order to decrypt the remainder of the file.

NUMBER_REGEX='public class p0*([1-9][0-9]*)'
PASS_REGEX=' check\((.* )?"?([^ "L]+)["L]?\);'
ANSWERS_FILE="answers.txt"

if [[ $1 == "-d" ]] # decrypt mode
then
    read -r number
    pass=$(cat $ANSWERS_FILE | grep "^$number\." | cut -d ' ' -f 2)
    if [[ $pass ]]
    then
        cat | openssl enc -nosalt -aes-256-cbc -d -pass pass:$pass
    else
        cat
    fi
    exit
fi

contents=`cat`
if [[ $contents =~ $NUMBER_REGEX ]]
then
    number=${BASH_REMATCH[1]}
    if [[ $contents =~ $PASS_REGEX ]]
    then
        pass=${BASH_REMATCH[2]}
        echo $number
        echo "$contents" | openssl enc -nosalt -aes-256-cbc -pass pass:$pass

        # add encryption password to answers file
        echo "$number. $pass" >> $ANSWERS_FILE
        sort -g --unique -o $ANSWERS_FILE $ANSWERS_FILE
    else
        echo "Failed to encrypt file." 1>&2
        exit 1
    fi
fi


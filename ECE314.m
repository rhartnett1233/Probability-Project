while 1
    prompt = 'Please enter a team name \n';
    team = input(prompt, 's');
    team = lower(team);
    for row=1:1:30
        if team == lower(data(row,1))
            % Creates pdfs for Points Scored and Points Against PG
            x = 65:1:150; % Set bounds and accuracy for x axis
            PPGMean = str2double(data(row,2)); % stores value from table
            PAPGMean = str2double(data(row,3));
            PPGStdDev = str2double(data(row,4));
            PAPGStdDev = str2double(data(row,5));
            PPG = normpdf(x,PPGMean,PPGStdDev); % creates Gaussian for PPG
            PAPG = normpdf(x,PAPGMean,PAPGStdDev); % creates Guassian for PAPG
            figure;
            plot(x,PPG,x,PAPG,'--') % plots both graphs on top of each other
            legend('Points Scored','Points Allowed') % add a legend
            title(strcat(data(row,1),' Statistics')) % add a title
            % Finds probability of winning any given game
            testMean = PPGMean-PAPGMean;
            testStdDev = sqrt((PPGStdDev^2)+(PAPGStdDev^2));
            prob = 1 - normcdf(0,testMean,testStdDev);
            fprintf(strcat('Chance to win any given game: \n',num2str(prob)));
            fprintf('\n\n');
            
            return
        end
    end
    fprintf('Failure! Try a new team\n\n')
end